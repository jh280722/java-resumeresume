package com.rere.document.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.box.domain.Boxes;
import com.rere.document.dto.DocumentRequest;
import com.rere.sortation.domain.Sortation;

import javax.persistence.*;

@Entity
public class Document {
    public static final long DEFAULT_ID = 0L;
    public static final String DEFAULT_NAME = "";
    @Embedded
    @JsonBackReference(value = "box_document")
    private final Boxes boxes = Boxes.of();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "sortation_id")
    private Sortation sortation;

    protected Document() {
    }

    private Document(Long id, String name, Sortation sortation) {
        this.id = id;
        this.name = name;
        this.sortation = sortation;
    }

    public static Document of() {
        return new Document();
    }

    public static Document of(DocumentRequest documentRequest) {
        return new Document(DEFAULT_ID, documentRequest.getName(), documentRequest.getSortation());
    }

    public static Document of(String name, Sortation sortation) {
        return new Document(DEFAULT_ID, name, sortation);
    }

    public static Document of(Long id, String name, Sortation sortation) {
        return new Document(id, name, sortation);
    }

    public Long getId() {
        return id;
    }

    public Boxes getBoxes() {
        return boxes;
    }

    public String getName() {
        return name;
    }
    public Sortation getSortation() {
        return sortation;
    }

    public void changeName(String name) {
        this.name = name;
    }


}
