package com.lgb.bootweb.lock;

public class DistributedLockException extends RuntimeException {

    /**
     * 构造一个基本异常.
     * @param message
     */
    public DistributedLockException(String message) {
        super(message);
    }

    /**
     * 构造一个基本异常.
     * @param message
     * @param cause
     */
    public DistributedLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
