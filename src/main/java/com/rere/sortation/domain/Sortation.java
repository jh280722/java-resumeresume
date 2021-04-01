package com.rere.sortation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.document.domain.Documents;
import com.rere.sortation.dto.SortationRequest;
import com.rere.user.domain.User;

import javax.persistence.*;

@Entity
public class Sortation {
    public static final String DEFAULT_NAME = "";
    public static final Long DEFAULT_ID = 0L;
    @Embedded
    @JsonBackReference(value = "document_sortation")
    private final Documents documents = Documents.of();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Sortation() {
    }

    private Sortation(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }
    private Sortation(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Sortation of() {
        return new Sortation();
    }


    public static Sortation of(SortationRequest sortationRequest) {
        return new Sortation(DEFAULT_ID, sortationRequest.getName());
    }

    public static Sortation of(String name, User user) {
        return new Sortation(DEFAULT_ID, name, user);
    }

    public static Sortation of(Long id, String name, User user) {
        return new Sortation(id, name, user);
    }


    public Long getId() {
        return id;
    }

    public Documents getDocuments() {
        return documents;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public void changeName(String name) {
        this.name = name;
    }

}
