package com.ciffarf.vehicle_service.Repository;

import com.ciffarf.vehicle_service.Model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository
        extends MongoRepository<Vehicle, String> {

    boolean existsByVehicleNumber(String vehicleNumber);

    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

    void deleteByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByOwnerName(String ownerName);

    List<Vehicle> findByOwnerNameContainingIgnoreCase(
            String ownerName);
}