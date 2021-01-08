package com.yuhan.service.warehouse.exceptions;

/**
 * @author yuhan
 * @date 12.11.2020 - 12:40
 * @purpose
 */
public class ItemNotAvailableException extends RuntimeException {
    public ItemNotAvailableException(String message) {
        super(message);
    }
}
