package com.lgb.bootweb.lock;

public interface DistributedLock {
    /**
     * 获得分布式锁
     * @param key
     * @return  true:获得锁成功, false:获得锁失败
     * @throws DistributedLockException
     */

    public boolean getLock(String key) ;

    /**
     * 获得分布式锁
     * @param key
     * @param timeoutSeconds
     * @return true:获得锁成功, false:获得锁失败
     * @throws DistributedLockException
     */
    public boolean getLock(String key, long timeoutSeconds) ;


    /**
     * 释放分布式锁//释放临时有序节点
     * @throws DistributedLockException
     */
    public void releaseLock() ;


    /**
     * 释放分布式锁//释放子单据的所有的节点
     * @throws DistributedLockException
     */
    public void releaseLockAll() ;
}
