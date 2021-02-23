package com.rere.document.domain;

import com.rere.box.dto.BoxResponse;
import com.rere.document.dto.DocumentResponse;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.dto.SortationResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Documents {
    @OneToMany(mappedBy = "sortation")
    private List<Document> documents = new ArrayList<>();

    protected Documents() {
    }

    private Documents(List<Document> documents) {
        this.documents = documents;
    }

    public static Documents of(List<Document> documents) {
        return new Documents(documents);
    }

    public static Documents of() {
        return new Documents();
    }

    public List<DocumentResponse> getDocumentResponses() {
        return documents.stream()
                .map(DocumentResponse::of)
                .collect(Collectors.toList());
    }

    public int size() {
        return documents.size();
    }

}
