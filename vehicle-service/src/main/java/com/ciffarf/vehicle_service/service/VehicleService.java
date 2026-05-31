package com.ciffarf.vehicle_service.service;

import com.ciffarf.vehicle_service.Model.Vehicle;
import com.ciffarf.vehicle_service.Repository.VehicleRepository;
import com.ciffarf.vehicle_service.dto.VehicleRequestDto;
import com.ciffarf.vehicle_service.dto.VehicleResponseDto;
import com.ciffarf.vehicle_service.event.VehicleCreatedEvent;
import com.ciffarf.vehicle_service.exception.VehicleNotFoundException;
import com.ciffarf.vehicle_service.mapper.VehicleMapper;
import com.ciffarf.vehicle_service.messaging.VehicleProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    private final VehicleProducer vehicleProducer;

    public VehicleResponseDto saveVehicleData(
            VehicleRequestDto dto) {

        log.info("Creating vehicle with number {}",
                dto.getVehicleNumber());

        if(vehicleRepository.existsByVehicleNumber(
                dto.getVehicleNumber())) {

            log.error("Vehicle already exists {}",
                    dto.getVehicleNumber());

            throw new RuntimeException(
                    "Vehicle already registered");
        }

        Vehicle vehicle =
                vehicleMapper.toEntity(dto);

        Vehicle savedVehicle =
                vehicleRepository.save(vehicle);

        vehicleProducer.sendVehicleCreatedEvent(
                new VehicleCreatedEvent(
                        savedVehicle.getVehicleNumber(),
                        savedVehicle.getOwnerName()
                ));

        log.info("Vehicle saved successfully {}",
                savedVehicle.getVehicleNumber());

        return vehicleMapper.toResponseDto(savedVehicle);
    }
    public List<VehicleResponseDto> getAllVehicles() {

        return vehicleRepository
                .findAll()
                .stream()
                .map(vehicleMapper::toResponseDto)
                .toList();
    }

    public VehicleResponseDto findByVehicleNumber(
            String vehicleNumber) {

        Vehicle vehicle =
                vehicleRepository
                        .findByVehicleNumber(vehicleNumber)
                        .orElseThrow(
                                () -> new VehicleNotFoundException(
                                        "Vehicle not found"));

        return vehicleMapper.toResponseDto(vehicle);
    }

    public Vehicle updateVehicle(
            String vehicleNumber,
            Vehicle updatedVehicle) {

        Vehicle existingVehicle =
                vehicleRepository
                        .findByVehicleNumber(vehicleNumber)
                        .orElseThrow(
                                () -> new VehicleNotFoundException(
                                        "Vehicle not found"));

        existingVehicle.setOwnerName(
                updatedVehicle.getOwnerName());

        existingVehicle.setEmail(
                updatedVehicle.getEmail());

        existingVehicle.setMobileNumber(
                updatedVehicle.getMobileNumber());

        return vehicleRepository.save(
                existingVehicle);
    }

    public String deleteVehicle(
            String vehicleNumber) {

        Vehicle vehicle =
                vehicleRepository
                        .findByVehicleNumber(vehicleNumber)
                        .orElseThrow(
                                () -> new VehicleNotFoundException(
                                        "Vehicle not found"));

        vehicleRepository.delete(vehicle);

        return "Vehicle deleted successfully";
    }

//    public Page<VehicleResponseDto> getAllVehicle(
//            int page, int size
//    ){
//        Pageable pageable= PageRequest.of(page, size);
//
//        return vehicleRepository
//                .findAll(pageable)
//                .map(vehicleMapper::toResponseDto);
//    }



    public Page<VehicleResponseDto> getVehicles(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return vehicleRepository
                .findAll(pageable)
                .map(vehicleMapper::toResponseDto);
    }


    public  List<VehicleResponseDto> searchByOwnerName(
            String ownerName
    )
    {
        return  vehicleRepository.findByOwnerName(ownerName)
                .stream()
                .map(vehicleMapper::toResponseDto)
                .toList();
    }

    public List<VehicleResponseDto> searchByOwnerName1(
            String ownerName) {

        return vehicleRepository
                .findByOwnerNameContainingIgnoreCase(
                        ownerName)
                .stream()
                .map(vehicleMapper::toResponseDto)
                .toList();
    }


}