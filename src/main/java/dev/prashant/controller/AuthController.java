package dev.prashant.controller;

import dev.prashant.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String generateToken(Authentication authentication) {
        LOGGER.info("Token requested for username : '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOGGER.info("Token generated : {}", token);
        return token;
    }
}
