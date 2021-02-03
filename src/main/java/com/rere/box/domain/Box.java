package com.rere.box.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.box.dto.BoxRequest;
import com.rere.item.domain.Items;

import javax.persistence.*;

@Entity
public class Box {
    public static final long DEFAULT_ID = 0L;
    public static final String DEFAULT_NAME = "";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Embedded
    @JsonBackReference
    private Items items = Items.of();

    //Long docId;

    protected Box() {
    }

    private Box(String name) {
        this.name = name;
    }

    public static Box of(BoxRequest boxRequest) {
        return new Box(boxRequest.getName());
    }

    public static Box of(String name) {
        return new Box(name);
    }

    public Long getId() {
        return id;
    }

    public Items getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
