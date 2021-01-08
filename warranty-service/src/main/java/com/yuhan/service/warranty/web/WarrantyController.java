package com.yuhan.service.warranty.web;

import com.yuhan.service.warranty.model.ErrorResponse;
import com.yuhan.service.warranty.model.ItemWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.WarrantyInfoResponse;
import com.yuhan.service.warranty.service.WarrantyService;
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
import java.util.UUID;

/**
 * @author yuhan
 * @date 13.11.2020 - 16:26
 * @purpose
 */
@Tag(name = "Warranty API")
@RestController
@RequestMapping("/api/v1/warranty")
public class WarrantyController {

    @Autowired
    WarrantyService warrantyService;

    @Operation(summary = "Check warranty status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warranty information"),
            @ApiResponse(responseCode = "404", description = "Warranty info not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "/{itemUid}", produces = "application/json")
    public WarrantyInfoResponse warrantyInfo(@PathVariable int itemUid) {
        return warrantyService.getWarrantyInfo(itemUid);
    }

    @Operation(summary = "Start warranty period")
    @ApiResponse(responseCode = "204", description = "Warranty started for item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{itemUid}")
    public void setWarranty(@PathVariable int itemUid) {
        warrantyService.startWarranty(itemUid);
    }

    @Operation(summary = "Close warranty")
    @ApiResponse(responseCode = "204", description = "Warranty closed for item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{itemUid}")
    public void stopWarranty(@PathVariable int itemUid) {
        warrantyService.stopWarranty(itemUid);
    }

    @Operation(summary = "Request warranty decision")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warranty decision"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Warranty not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "/{itemUid}/warranty", consumes = "application/json", produces = "application/json")
    public OrderWarrantyResponse warrantyRequest(@PathVariable int itemUid, @Valid @RequestBody ItemWarrantyRequest request) {
        return warrantyService.warrantyRequest(itemUid, request);
    }
}

