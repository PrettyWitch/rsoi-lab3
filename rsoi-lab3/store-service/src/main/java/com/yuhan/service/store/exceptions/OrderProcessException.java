package com.yuhan.service.store.exceptions;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:01
 * @purpose
 */
public class OrderProcessException extends RuntimeException {
    public OrderProcessException(String message) {
        super(message);
    }
}
