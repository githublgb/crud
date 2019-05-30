package com.lgb.zookeeper;

/**
 * <p>Title:	DubboConfigBean.java</p >
 * <p>Description:	TODO </p >
 * @author	ex_sanzw
 * @date	2018年3月21日 下午7:29:09
 */
public class DubboConfigBean {
    // 服务分组
    private String group;
    // 版本
    private String version;
    // 服务唯一标识 必填
    private String serviceId;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

}
