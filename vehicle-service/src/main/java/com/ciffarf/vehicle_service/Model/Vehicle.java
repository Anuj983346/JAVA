package com.ciffarf.vehicle_service.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String id;

    private String vehicleNumber;
    private String ownerName;
    private String email;
    private String mobileNumber;
}
