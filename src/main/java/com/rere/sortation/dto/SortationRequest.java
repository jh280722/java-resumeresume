package com.rere.sortation.dto;

import com.rere.user.domain.User;

public class SortationRequest {
    private String name;
    private User user;

    public SortationRequest() {
    }

    public SortationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }
}
