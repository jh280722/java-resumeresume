package com.rere.auth.application;

import com.rere.auth.exception.InvalidTokenException;
import com.rere.auth.infrastructure.JwtTokenProvider;
import com.rere.user.domain.User;
import com.rere.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public boolean checkInvalidMember(String email, String password) {
        return !isUser(userRepository.findByEmail(email), password);
    }

    private boolean isUser(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }

    public User checkInvalidToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException("인증토큰이 맞지않습니다.");
        }
        return userRepository.findByEmail(jwtTokenProvider.getPayload(accessToken));
    }
}
