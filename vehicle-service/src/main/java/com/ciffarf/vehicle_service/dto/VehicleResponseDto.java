package com.ciffarf.vehicle_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponseDto {

    private String id;
    private String vehicleNumber;
    private String ownerName;
}