package com.rere.user.application;

import com.rere.user.domain.User;
import com.rere.user.domain.UserRepository;
import com.rere.user.dto.UserRequest;
import com.rere.user.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse save(UserRequest userRequest) {
        return UserResponse.of(userRepository.save(User.of(userRequest)));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(User.of());
    }

    @Transactional
    public void updateUser(Long id, UserRequest userRequest) {
        User updateUser = userRepository.findById(id).orElse(null);
        updateUser.changeUser(User.of(userRequest));
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse findUser(Long id) {
        return UserResponse.of(userRepository.findById(id).orElse(null));
    }
}
