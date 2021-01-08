package com.yuhan.service.order.web;

import com.yuhan.service.order.model.*;
import com.yuhan.service.order.service.OrderManagementService;
import com.yuhan.service.order.service.OrderService;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.server.UID;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 12:40
 * @purpose
 */
@Tag(name = "Order API")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderManagementService orderManagementService;

    @Operation(summary = "User order info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order info"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{userUid}/{orderUid}", produces = {"application/json"})
    public OrderInfoResponse userOrder(@PathVariable int userUid, @PathVariable int orderUid) {
        return orderService.getUserOrder(userUid, orderUid);
    }

    @Operation(summary = "User orders info")
    @ApiResponse(responseCode = "200", description = "Orders info")
    @GetMapping(value = "/{userUid}", produces = {"application/json"})
    public OrdersInfoResponse userOrders(@PathVariable int userUid) {
        return orderService.getUserOrders(userUid);
    }

    @Operation(summary = "Create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = {"/{userUid}"}, consumes = {"application/json"}, produces = {"application/json"})
    public CreateOrderResponse makeOrder(@PathVariable int userUid, @Valid @RequestBody CreateOrderRequest request) {
        return orderManagementService.makeOrder(userUid, request);
    }

    @Operation(summary = "Return order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order returned"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = {"/{orderUid}"})
    private void refundOrder(@PathVariable int orderUid) {
        orderManagementService.refundOrder(orderUid);
    }

    @Operation(summary = "Warranty request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warranty decision"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = {"/{orderUid}/warranty"}, consumes = {"application/json"}, produces = {"application/json"})
    private RabbitWarrantyRequest warranty(@PathVariable int orderUid, @Valid @RequestBody OrderWarrantyRequest request) {
        return orderManagementService.useWarranty(orderUid, request);
    }

}
