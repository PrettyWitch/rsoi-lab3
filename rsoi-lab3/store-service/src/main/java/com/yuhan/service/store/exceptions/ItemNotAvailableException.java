package com.yuhan.service.store.exceptions;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:01
 * @purpose
 */
public class ItemNotAvailableException extends RuntimeException {
    String message;

    public ItemNotAvailableException(String message) {
        this.message = message;
    }
}
