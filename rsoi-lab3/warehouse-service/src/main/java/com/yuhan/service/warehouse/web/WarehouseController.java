package com.yuhan.service.warehouse.web;


import com.yuhan.service.store.model.WarrantyRequest;
import com.yuhan.service.warehouse.domain.Item;
import com.yuhan.service.warehouse.model.ErrorResponse;
import com.yuhan.service.warehouse.model.ItemInfoResponse;
import com.yuhan.service.warehouse.model.OrderItemRequest;
import com.yuhan.service.warehouse.model.OrderItemResponse;
import com.yuhan.service.warehouse.service.WarehouseService;
import com.yuhan.service.warehouse.service.WarrantyService;
import com.yuhan.service.warranty.model.ItemWarrantyRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

/**
 * @author yuhan
 * @date 13.11.2020 - 13:35
 * @purpose
 */
@Tag(name = "Warehouse API")
@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    WarrantyService warrantyService;


    @Operation(summary = "Insert item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User create"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "item", consumes = {"application/json"})
    public ResponseEntity<Void> createItem(@Valid @RequestBody Item item) {
        Item u = warehouseService.createItem(item);
        int id = u.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Get item information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item information"),
            @ApiResponse(responseCode = "404", description = "Item info not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "/{orderItemUid}", produces = "application/json")
    private ItemInfoResponse item(@PathVariable int orderItemUid) {
        return warehouseService.getItemInfo(orderItemUid);
    }

    @Operation(summary = "Take item from warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item add to order"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Requested item info not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Requested item info not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public OrderItemResponse takeItem(@Valid @RequestBody OrderItemRequest request) {
        return warehouseService.takeItem(request);
    }

    @Operation(summary = "Return item")
    @ApiResponse(responseCode = "204", description = "Item returned")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderItemUid}")
    public void returnItem(@PathVariable int orderItemUid) {
        warehouseService.returnItem(orderItemUid);
    }

    @Operation(summary = "Request item warranty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item add to order"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Requested item info not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Requested item info not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @PostMapping("/{orderItemUid}/warranty")
    public RabbitWarrantyRequest warranty(@PathVariable int orderItemUid, @Valid @RequestBody OrderWarrantyRequest request) {
        int availableCount = warehouseService.getItemInfo(orderItemUid).getAvailableCount();
        String reason = request.getReason();
        ItemWarrantyRequest itemWarrantyRequest = new ItemWarrantyRequest(reason, availableCount);
        RabbitWarrantyRequest rabbitWarrantyRequest = new RabbitWarrantyRequest(orderItemUid, itemWarrantyRequest);
        System.out.println(rabbitWarrantyRequest.toString());
        System.out.println(itemWarrantyRequest.toString());
        return rabbitWarrantyRequest;
    }

}
