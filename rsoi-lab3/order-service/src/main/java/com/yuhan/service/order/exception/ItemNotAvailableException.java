package com.yuhan.service.order.exception;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:05
 * @purpose
 */
public class ItemNotAvailableException extends RuntimeException {
    String message;

    public ItemNotAvailableException(String message) {
        this.message = message;
    }
}
