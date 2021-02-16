package com.rere.document.dto;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;

import java.util.List;

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
