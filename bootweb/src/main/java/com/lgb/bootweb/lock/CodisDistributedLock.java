package com.lgb.bootweb.lock;


import com.lgb.bootweb.util.SpringUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Collections;
import java.util.List;

public class CodisDistributedLock implements DistributedLock {


    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    private final ZkClient zk;
    private String lockName;
    private String zkPath;
    private final long threadId;
    private String lockPath;

    @Autowired
    private ZkClient zkClient;

    /**
     * 分布式锁
     */
    public CodisDistributedLock() {

        this.zk = SpringUtil.getBean(ZkClient.class);
        this.threadId = Thread.currentThread().getId();
    }

    /*
     * 监听子节点的变动
     */
    private class ZkChildListener implements IZkChildListener {
        final Object lock;
        final long threadId;
        final String lockName;

        public ZkChildListener(Object lockObj, String lockNodeName) {
            this.lock = lockObj;
            this.lockName = lockNodeName;
            this.threadId = Thread.currentThread().getId();
        }

        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            synchronized (this.lock) {
                String firstNodeName = getFirstNodeName(currentChilds, this.lockName);
                if (firstNodeName.length() > 0 && lockPath.endsWith(firstNodeName)) {
                    LOGGER.debug(String.format("线程%s，%s", threadId, "监听到子节点变化，成功获取锁"));
                    this.lock.notifyAll();
                } else {
                    LOGGER.debug(String.format("线程%s，%s", threadId, "监听到子节点变化，继续等待锁"));
                }
            }
        }
    }

    private static String getFirstNodeName(List<String> nodeNames, String lockName) {
        // 节点名称格式为lockName000000000X，可按字符串简单排序
        Collections.sort(nodeNames);
        for (String nodeName : nodeNames) {
            if (nodeName.startsWith(lockName)) {
                return nodeName;
            }
        }
        return "";
    }

    /**
     * 尝试获取锁，获取失败的话，不需要再执行releaseLock
     *
     * @return true:获得锁成功, false:获得锁失败
     */
    public boolean getLock(String lockName) {
        if (zk == null) {
            throw new DistributedLockException("don't get zkClient instance object");
        }

        this.lockName = lockName;
        this.zkPath = "/com/lgb/DistributedLock" + this.lockName;

        zk.createPersistent(this.zkPath, true);
        lockPath = zk.createEphemeralSequential(this.zkPath + "/" + this.lockName, null);
        List<String> nodes = zk.getChildren(this.zkPath);
        String firstNodeName = getFirstNodeName(nodes, this.lockName);
        nodes = null;
        if (lockPath.endsWith(firstNodeName)) {
            // 成功获取锁
            return true;
        }
        // 否则立即删除当前创建的锁节点
        releaseLock();
        return false;
    }

    /*
     * 等待，直到锁成功获取
     */
    public void waitUntilLock() throws InterruptedException {
        if (zk == null) {
            throw new DistributedLockException("don't get zkClient instance object");
        }
        zk.createPersistent(this.zkPath, true);
        lockPath = zk.createEphemeralSequential(this.zkPath + "/" + this.lockName, null);
        LOGGER.debug(String.format("线程%s，%s", threadId, "初始化子节点"));
        final Object lock = new Object();

        synchronized (lock) {
            LOGGER.debug(String.format("线程%s，%s", threadId, "查看子节点的情况"));
            // 先监听子节点变化
            IZkChildListener listener = new ZkChildListener(lock, this.lockName);
            zk.subscribeChildChanges(this.zkPath, listener);
            // 获取子节点
            List<String> nodes = zk.getChildren(this.zkPath);
            String firstNodeName = getFirstNodeName(nodes, this.lockName);
            nodes = null;
            try {
                if (lockPath.endsWith(firstNodeName)) {
                    return;
                }
                LOGGER.debug(String.format("线程%s，%s", threadId, "等待锁"));
                lock.wait();// 等待并释放对lock对象的独占
            } finally {
                if (listener != null) {
                    zk.unsubscribeChildChanges(this.zkPath, listener);
                    listener = null;
                }
                LOGGER.debug(String.format("线程%s，%s", threadId, "成功获取锁后，释放监听"));
            }
        }
    }

    @Override
    public boolean getLock(String key, long timeoutSeconds) {
        return getLock(key);
    }

    /*
     * 主动释放锁,此方法只是释放了临时有序节点，
     */
    @Override
    public void releaseLock() {
        if (zk == null) {
            throw new DistributedLockException("don't get zkClient instance object");
        }
        if (lockPath != null) {
           // zk.delete(lockPath );
            zk.delete(lockPath);
            LOGGER.debug(String.format("线程%s，%s", threadId, "释放锁"));
            lockPath = null;
        }
    }


    @Override
    public void releaseLockAll() {

        releaseLock();
        if (zk == null) {
            throw new DistributedLockException("don't get zkClient instance object");
        }
        if (zkPath != null) {
            // zk.delete(lockPath );
            zk.delete(zkPath);
            LOGGER.debug(String.format("线程%s，%s", threadId, "释放锁"));
            zkPath = null;
        }

    }
}
