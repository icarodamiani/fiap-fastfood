package io.fiap.fastfood.driver.controller.tracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OrderTrackingDTO(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String id,

        @NotNull
        String orderId,

        @NotNull
        String orderNumber,

        @NotNull
        OrderTrackingStatusTypeDTO orderStatus,

        @NotNull
        OrderTrackingRoleTypeDTO role,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @JsonFormat(pattern = "yyyy-M-d'T'HH:mm:ss.yyyy'Z'")
        LocalDateTime orderDateTime
) {

    @Override
    public String id() {
        return id;
    }

    @Override
    public String orderId() {
        return orderId;
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public OrderTrackingStatusTypeDTO orderStatus() {
        return orderStatus;
    }

    @Override
    public OrderTrackingRoleTypeDTO role() {
        return role;
    }

    @Override
    public LocalDateTime orderDateTime() {
        return orderDateTime;
    }
}
