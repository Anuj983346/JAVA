package com.ciffarf.vehicle_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleRequestDto {

    @NotBlank(message = "Vehicle Number is required")
    private String vehicleNumber;

    @NotBlank(message = "Owner Name is required")
    private String ownerName;

    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    private String mobileNumber;
}