package com.rere.document.dto;

public class DocumentRequest {
    private String name;

    public DocumentRequest() {
    }

    public DocumentRequest(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
