package com.ciffarf.vehicle_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreatedEvent {

    private String vehicleNumber;
    private String ownerName;
}