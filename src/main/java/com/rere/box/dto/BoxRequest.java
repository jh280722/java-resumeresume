package com.rere.box.dto;

import com.rere.document.domain.Document;

public class BoxRequest {
    private String name;
    private Document document;

    public BoxRequest() {
    }

    public BoxRequest(String name, Document document) {
        this.name = name;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public Document getDocument() {
        return document;
    }
}
