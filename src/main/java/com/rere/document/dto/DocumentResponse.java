package com.rere.document.dto;

import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.Document;
import com.rere.sortation.domain.Sortation;

import java.util.List;

public class DocumentResponse {
    private Long id;
    private String name;
    private List<BoxResponse> boxes;
    private Sortation sortation;

    public DocumentResponse() {
    }

    public DocumentResponse(Long id, String name, List<BoxResponse> boxes, Sortation sortation) {
        this.id = id;
        this.name = name;
        this.boxes = boxes;
        this.sortation = sortation;
    }

    public static DocumentResponse of(Document document) {
        return new DocumentResponse(document.getId(), document.getName(), document.getBoxes().getBoxResponses(), document.getSortation());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BoxResponse> getBoxes() {
        return boxes;
    }

    public Sortation getSortation() {
        return sortation;
    }
}
