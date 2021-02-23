package com.rere.user.dto;

import com.rere.user.domain.User;

public class UserResponse {
    private Long id;
    private String email;
    private String name;

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
