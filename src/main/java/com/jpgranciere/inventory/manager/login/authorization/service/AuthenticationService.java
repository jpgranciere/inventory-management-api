package com.jpgranciere.inventory.manager.login.authorization.service;

import com.jpgranciere.inventory.manager.exception.UserAlreadyRegistrationException;
import com.jpgranciere.inventory.manager.login.dto.AuthenticationCreateRequest;
import com.jpgranciere.inventory.manager.login.dto.AuthenticationRegisterCreate;
import com.jpgranciere.inventory.manager.login.dto.LoginResponse;
import com.jpgranciere.inventory.manager.login.dto.RegisterResponse;
import com.jpgranciere.inventory.manager.login.user.entity.User;
import com.jpgranciere.inventory.manager.login.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginResponse login(AuthenticationCreateRequest authentication){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authentication.login(), authentication.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponse(token);
    }

    public RegisterResponse register(AuthenticationRegisterCreate authenticationRegister){
        if(userRepository.findByLogin(authenticationRegister.login()) != null){
            throw new UserAlreadyRegistrationException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(authenticationRegister.password());
        User user = new User(authenticationRegister.login(), encryptedPassword, authenticationRegister.userRole());

        userRepository.save(user);

        return new RegisterResponse(user);
    }
}



