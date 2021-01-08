package com.yuhan.service.store.web;

import com.yuhan.service.order.model.CreateOrderRequest;
import com.yuhan.service.order.model.ErrorResponse;
import com.yuhan.service.store.domain.User;
import com.yuhan.service.store.model.*;
import com.yuhan.service.store.service.StoreService;
import com.yuhan.service.store.service.UserService;
import com.yuhan.service.store.service.WarehouseService;
import com.yuhan.service.store.service.WarrantyService;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * @author yuhan
 * @date 11.11.2020 - 14:26
 * @purpose
 */
@Tag(name = "Store API")
@RestController
@RequestMapping("/api/v1/store")
public class StoreController {
    @Autowired
    StoreService storeService;
    @Autowired
    UserService userService;
    @Qualifier("com.yuhan.service.store.service.WarehouseService")
    @Autowired
    WarehouseService warehouseService;
    @Qualifier("com.yuhan.service.store.service.WarrantyService")
    @Autowired
    WarrantyService warrantyService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Operation(summary = "List users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{userUid}", produces = {"application/json"})
    public User user(@PathVariable int userUid) {
        return userService.getUserById(userUid);
    }

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User create"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "user", consumes = {"application/json"})
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        User u = userService.createUser(user);
        int id = u.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "List user orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User orders info"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{userUid}/orders", produces = {"application/json"})
    public List<UserOrderResponse> orders(@PathVariable int userUid) {
        return storeService.findUserOrders(userUid);
    }

    @Operation(summary = "User order info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User orders info"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{userUid}/{orderUid}", produces = {"application/json"})
    public UserOrderResponse order(@PathVariable int userUid, @PathVariable int orderUid) {
        return storeService.findUserOrder(userUid, orderUid);
    }

    @Operation(summary = "Purchase item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item purchase"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "/{userUid}/purchase", consumes = {"application/json"})
    public ResponseEntity<Void> purchase(@PathVariable int userUid, @Valid @RequestBody CreateOrderRequest request) {
        int orderUid = storeService.makePurchase(userUid, request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderUid}").buildAndExpand(orderUid).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Return items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item returned"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userUid}/{orderUid}/refund")
    public void refund(@PathVariable int userUid, @PathVariable int orderUid) {
        storeService.refundPurchase(userUid, orderUid);
    }

    @Operation(summary = "Request warranty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warranty decision"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "/{userUid}/{orderUid}/warranty", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    public String warranty(@PathVariable int userUid, @PathVariable int orderUid, @Valid @RequestBody OrderWarrantyRequest request) {
        //此处对rabbitTemplate进行指定序列化工具(为了全局，你可以在容器启动时进行设置)
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting",storeService.warrantyRequest1(userUid, orderUid, request));
        System.out.println(storeService.warrantyRequest1(userUid, orderUid, request));
        System.out.println(storeService.warrantyRequest1(userUid, orderUid, request).getClass().toString());
        return "Application is successful";
    }

}
