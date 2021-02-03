package com.rere.item.domain;

import com.rere.box.domain.Box;
import com.rere.item.dto.ItemRequest;

import javax.persistence.*;

@Entity
public class Item {
    public static final long DEFAULT_ID = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    private String value;

    @ManyToOne
    @JoinColumn(name = "box_id")
    private Box box;

    protected Item() {}
    private Item(Long id, String type, String name, String value, Box box) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
    }

    private Item(String type, String name, String value, Box box) {
        this(DEFAULT_ID, type, name, value, box);
    }

    public static Item of(ItemRequest itemRequest) {
        return new Item(itemRequest.getType(), itemRequest.getName(), itemRequest.getValue(), itemRequest.getBox());
    }

    public static Item of(String type, String name, String value, Box box) {
        return new Item(type, name, value, box);
    }

    public static Item of(Long id, String type, String name, String value, Box box) {
        return new Item(id, type, name, value, box);
    }

    public String setDefaultName() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public Box getBox() {
        return box;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public void changeValue(String value) {
        this.value = value;
    }

    public void changeBox(Box box) {
        this.box = box;
    }

    public void changeItem(Item item) {
        this.type = item.type;
        this.name = item.name;
        this.value = item.value;
        this.box = item.box;
    }
}
