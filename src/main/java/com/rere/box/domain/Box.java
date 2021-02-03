package com.rere.box.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.box.dto.BoxRequest;
import com.rere.item.domain.Item;
import com.rere.item.domain.Items;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Items items;

    //Long docId;

    protected Box() {
    }

    private Box(String name) {
        this.name = name;
    }

    private Box(Long id) {
        this(id, DEFAULT_NAME, new ArrayList<>());
    }

    private Box(List<Item> items) {
        this(DEFAULT_ID, DEFAULT_NAME, items);
    }

    private Box(Long id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.items = Items.of(items);
    }

    public static Box of(Long id) {
        return new Box(id);
    }

    public static Box of(Long id, List<Item> items) {
        return new Box(id, DEFAULT_NAME, items);
    }

    public static Box of(Long id, String name, List<Item> items) {
        return new Box(id, name, items);
    }

    public static Box of(List<Item> items) {
        return new Box(items);
    }

    public static Box of(BoxRequest boxRequest) {
        return new Box(boxRequest.getItems());
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
