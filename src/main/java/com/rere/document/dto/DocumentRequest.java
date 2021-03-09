package com.rere.document.dto;

import com.rere.sortation.domain.Sortation;

public class DocumentRequest {
    private String name;
    private Sortation sortation;

    public DocumentRequest() {
    }

    public DocumentRequest(String name, Sortation sortation) {
        this.name = name;
        this.sortation = sortation;
    }

    public String getName() {
        return name;
    }

    public Sortation getSortation() {
        return sortation;
    }
}
