package com.securitywithjwt.SecurityWithJWT.controllers;

import com.securitywithjwt.SecurityWithJWT.configs.security.TokenService;
import com.securitywithjwt.SecurityWithJWT.models.UserModel;
import com.securitywithjwt.SecurityWithJWT.models.dtos.AuthenticationDTO;
import com.securitywithjwt.SecurityWithJWT.models.dtos.LoginResponseDTO;
import com.securitywithjwt.SecurityWithJWT.models.dtos.RegisterDTO;
import com.securitywithjwt.SecurityWithJWT.models.enums.Roles;
import com.securitywithjwt.SecurityWithJWT.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository repository;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository repository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data){
        String encryptedPass = new BCryptPasswordEncoder().encode(data.password());
        UserModel user = new UserModel(null, data.name(), data.username(), encryptedPass, data.email(), Roles.USER);
        return ResponseEntity.ok(repository.save(user));
    }
}
