package com.rere.user.dto;

import com.rere.sortation.dto.SortationResponse;
import com.rere.user.domain.User;

import java.util.List;

public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private List<SortationResponse> sortations;

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String name, List<SortationResponse> sortations) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.sortations = sortations;
    }

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getSortations().getSortationResponses());
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

    public List<SortationResponse> getSortations() {
        return sortations;
    }
}
