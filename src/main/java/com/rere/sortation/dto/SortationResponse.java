package com.rere.sortation.dto;

import com.rere.document.dto.DocumentResponse;
import com.rere.sortation.domain.Sortation;
import com.rere.user.domain.User;

import java.util.List;

public class SortationResponse {
    private Long id;
    private String name;
    private List<DocumentResponse> documents;
    private User user;

    public SortationResponse() {
    }

    public SortationResponse(Long id, String name, List<DocumentResponse> documents, User user) {
        this.id = id;
        this.name = name;
        this.documents = documents;
        this.user = user;
    }

    public static SortationResponse of(Sortation sortation) {
        return new SortationResponse(sortation.getId(), sortation.getName(), sortation.getDocuments().getDocumentResponses(), sortation.getUser());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DocumentResponse> getDocuments() {
        return documents;
    }

    public User getUser() {
        return user;
    }
}
