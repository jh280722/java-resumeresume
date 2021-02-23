package com.rere.document.dto;

import com.rere.sortation.domain.Sortation;

public class DocumentRequest {
    private String name;
    private Sortation sortation;
    public DocumentRequest() {
    }

    public DocumentRequest(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
    public Sortation getSortation(){
        return sortation;
    }
}
