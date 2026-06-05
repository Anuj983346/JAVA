package com.ciffarf.vehicle_service.controller;

import com.ciffarf.vehicle_service.Client.OrderFeignClient;
import com.ciffarf.vehicle_service.Model.Vehicle;
import com.ciffarf.vehicle_service.dto.VehicleRequestDto;
import com.ciffarf.vehicle_service.dto.VehicleResponseDto;
import com.ciffarf.vehicle_service.service.VehicleService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    public final OrderFeignClient orderFeignClient;

    @PostMapping
    public VehicleResponseDto createVehicle(
            @Valid
            @RequestBody VehicleRequestDto dto) {

        return vehicleService.saveVehicleData(dto);
    }

    @GetMapping("/search")
    public List<VehicleResponseDto> getAllVehicles() {

        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{vehicleNumber}")
    public VehicleResponseDto getVehicleByNumber(
            @PathVariable String vehicleNumber) {

        return vehicleService.findByVehicleNumber(
                vehicleNumber);
    }

    @GetMapping("/orders")
    @Retry(name = "orderService")
    @CircuitBreaker(
            name = "orderService",
            fallbackMethod = "fallbackOrders")
    @RateLimiter(
            name = "orderService",
            fallbackMethod = "rateLimitFallback")
    public String getOrders() {

        System.out.println("Calling Order Service...");
        return orderFeignClient.getOrders();
    }



    public  String fallbackOrders(Exception e){
        return  "Order Service is temporarily unavialable";
    }
    public String rateLimitFallback(Exception ex) {
        return "Too many requests. Please try again later.";
    }

    @PutMapping("/{vehicleNumber}")
    public Vehicle updateVehicle(
            @PathVariable String vehicleNumber,
            @RequestBody Vehicle vehicle) {

        return vehicleService.updateVehicle(
                vehicleNumber,
                vehicle);
    }

    @DeleteMapping("/{vehicleNumber}")
    public String deleteVehicle(
            @PathVariable String vehicleNumber) {

        return vehicleService.deleteVehicle(
                vehicleNumber);
    }




    @GetMapping("/page")
    public Page<VehicleResponseDto> getVehicles(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "ownerName")
            String sortBy) {

        return vehicleService
                .getVehicles(
                        page,
                        size,
                        sortBy);
    }


    @GetMapping
    public List<VehicleResponseDto> searchByOwnerName(
            @RequestParam String ownerName
    ){
        return vehicleService.searchByOwnerName(ownerName);
    }






}