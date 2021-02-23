package com.rere.sortation.dto;

import com.rere.document.domain.Document;

public class SortationRequest {
    private String name;
    private User user;

    public SortationRequest() {
    }

    public SortationRequest(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public Document getUser() {
        return user;
    }
}
