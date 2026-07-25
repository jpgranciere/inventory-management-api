package com.jpgranciere.inventory.manager.login.controller;

import com.jpgranciere.inventory.manager.login.authorization.service.AuthenticationService;
import com.jpgranciere.inventory.manager.login.dto.AuthenticationCreateRequest;
import com.jpgranciere.inventory.manager.login.dto.AuthenticationRegisterCreate;
import com.jpgranciere.inventory.manager.login.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationCreateRequest authentication){

        var auth = authenticationService.login(authentication);

        return ResponseEntity.ok(auth);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationRegisterCreate authenticationRegister){

        var user = authenticationService.register(authenticationRegister);

        return ResponseEntity.ok(user);
    }
}
