package com.rere.document.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.box.domain.Box;
import com.rere.box.domain.Boxes;
import com.rere.box.dto.BoxRequest;
import com.rere.document.dto.DocumentRequest;
import com.rere.item.domain.Items;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Document {
    public static final long DEFAULT_ID = 0L;
    public static final String DEFAULT_NAME = "";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Embedded
    @JsonBackReference(value="box_document")
    private Boxes boxes=Boxes.of();

    protected Document() {
    }
    private Document(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    private Document(String name) {
        this(DEFAULT_ID, name);
    }

    public static Document of() {
        return new Document();
    }
    public static Document of(Long id, String name) {
        return new Document(id, name);
    }


    public static Document of(DocumentRequest documentRequest) {
        return new Document(documentRequest.getName());
    }

    public static Document of(String name) {
        return new Document(name);
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

    public void changeName(String name) {
        this.name = name;
    }


}
