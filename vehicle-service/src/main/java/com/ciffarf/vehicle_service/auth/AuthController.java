package com.ciffarf.vehicle_service.auth;


import com.ciffarf.vehicle_service.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request) {

        if ("admin".equals(request.getUsername())
                && "admin123".equals(request.getPassword())) {

            String token =
                    jwtService.generateToken(
                            request.getUsername());

            return new AuthResponse(token);
        }

        throw new RuntimeException(
                "Invalid username or password");
    }
}