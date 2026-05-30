package com.ciffarf.vehicle_service.mapper;

import com.ciffarf.vehicle_service.Model.Vehicle;
import com.ciffarf.vehicle_service.dto.VehicleRequestDto;
import com.ciffarf.vehicle_service.dto.VehicleResponseDto;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(
            VehicleRequestDto dto) {

        return Vehicle.builder()
                .vehicleNumber(dto.getVehicleNumber())
                .ownerName(dto.getOwnerName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .build();
    }

    public VehicleResponseDto toResponseDto(
            Vehicle vehicle) {

        return VehicleResponseDto.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getVehicleNumber())
                .ownerName(vehicle.getOwnerName())
                .build();
    }
}