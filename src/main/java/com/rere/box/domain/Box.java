package com.rere.box.domain;

import com.rere.box.dto.BoxRequest;
import com.rere.item.domain.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    public static final long DEFAULT_ID = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "box")
    private List<Item> items;
    //Long docId;

    protected Box() {
    }

    public Box(String name) {
        this.name = name;
    }

    private Box(Long id) {
        this(id, new ArrayList<>());
    }

    private Box(List<Item> items) {
        this(DEFAULT_ID, items);
    }

    private Box(Long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Box of(Long id) {
        return new Box(id);
    }

    public static Box of(Long id, List<Item> items) {
        return new Box(id, items);
    }

    public static Box of(List<Item> items) {
        return new Box(items);
    }

    public static Box of(BoxRequest boxRequest) {
        return new Box(boxRequest.getItems());
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
