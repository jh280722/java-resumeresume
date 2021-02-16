package com.rere.document.dto;

import com.rere.box.domain.Box;
import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.Document;

import java.util.List;

public class DocumentResponse {
    private Long id;
    private String name;
    private List<BoxResponse> boxes;

    public DocumentResponse() {
    }

    public DocumentResponse(Long id, String name, List<BoxResponse> boxes) {
        this.id = id;
        this.name = name;
        this.boxes = boxes;
    }

    public static DocumentResponse of(Document document) {
        return new DocumentResponse(document.getId(), document.getName(), document.getBoxes().getBoxResponses());
    }
    public Long getId() {
        return id;
    }
    public String getName(){
        return name;
    }

    public List<BoxResponse> getBoxes() {
        return boxes;
    }
}
