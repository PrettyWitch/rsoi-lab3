package com.yuhan.service.order.web;

import com.yuhan.service.order.exception.WarehouseProcessException;
import com.yuhan.service.order.exception.WarrantyProcessException;
import com.yuhan.service.order.model.ErrorResponse;
import com.yuhan.service.order.exception.ItemNotAvailableException;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author yuhan
 * @date 10.11.2020 - 11:18
 * @purpose
 */
@Hidden
@RestControllerAdvice(annotations = RestControllerAdvice.class)
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    private List<FieldError> fieldErrorList;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse badRequest(MethodArgumentNotValidException exception) {
        String validationErrors = prepareValidationErrors(exception.getBindingResult().getFieldErrors());
        if (logger.isDebugEnabled()) {
            logger.debug("Bad Request: {}", validationErrors);
        }
        return new ErrorResponse(validationErrors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFound(EntityNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ItemNotAvailableException.class)
    public ErrorResponse conflict(ItemNotAvailableException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {
            WarehouseProcessException.class, WarrantyProcessException.class
    })
    public ErrorResponse conflict(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleException(RuntimeException exception) {
        logger.error("", exception);
        return new ErrorResponse(exception.getMessage());
    }

    private String prepareValidationErrors(List<FieldError> errors) {
        String str = "";
        for (FieldError error : errors) {
            String field = error.getField();
            String defaultMessage = error.getDefaultMessage();
            str += String.format("Field %s has wrong value: [%s]\n", field, defaultMessage);
        }
        return str;
    }
}
