package com.rere.auth.ui;

import com.rere.auth.application.AuthService;
import com.rere.auth.dto.TokenRequest;
import com.rere.auth.dto.TokenResponse;
import com.rere.auth.exception.InvalidUserException;
import com.rere.auth.infrastructure.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private AuthService authService;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        String email = tokenRequest.getEmail();
        String password = tokenRequest.getPassword();
        if (authService.checkInvalidMember(email, password)) {
            throw new InvalidUserException("로그인 정보가 맞지 않습니다.");
        }
        return ResponseEntity.ok().body(new TokenResponse(jwtTokenProvider.createToken(email)));
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Void> invalidToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
