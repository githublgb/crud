package com.lgb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class IpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    public static String getHostIp() {
        String ip = null;
        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                Enumeration enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr
                            .nextElement();
                    if ((!inetAddress.isLoopbackAddress()) &&
                            (!inetAddress
                                    .isLinkLocalAddress()) &&
                            (inetAddress
                                    .isSiteLocalAddress()))
                        ip = inetAddress.getHostAddress();
                }
            }
        } catch (SocketException e) {
            LOGGER.error("Fail to get IP address.", e);
        }
        return ip;
    }

    public static String getHostName() {
        String hostName = null;
        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                Enumeration enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr
                            .nextElement();
                    if ((!inetAddress.isLoopbackAddress()) &&
                            (!inetAddress
                                    .isLinkLocalAddress()) &&
                            (inetAddress
                                    .isSiteLocalAddress()))
                        hostName = inetAddress.getHostName();
                }
            }
        } catch (SocketException e) {
            LOGGER.error("Fail to get host name.", e);
        }
        return hostName;
    }
}
