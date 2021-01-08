package com.yuhan.service.order.exception;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:05
 * @purpose
 */
public class CreateOrderException extends RuntimeException {
    private String message;

    public CreateOrderException(String message) {
        super(message);
    }

}
