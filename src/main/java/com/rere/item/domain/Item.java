package com.rere.item.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rere.box.domain.Box;
import com.rere.item.dto.ItemRequest;

import javax.persistence.*;

@Entity
public class Item {
    public static final long DEFAULT_ID = 0L;
    public static final int DEFAULT_SEQ = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int seq;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    private String value;

    @ManyToOne
    @JoinColumn(name = "box_id")
    @JsonManagedReference
    private Box box;

    protected Item() {}
    private Item(Long id, int seq, String type, String name, String value, Box box) {
        this.id = id;
        this.seq = seq;
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
    }

    private Item(int seq, String type, String name, String value, Box box) {
        this(DEFAULT_ID, seq, type, name, value, box);
    }

    public static Item of(ItemRequest itemRequest) {
        return new Item(itemRequest.getSeq(), itemRequest.getType(), itemRequest.getName(), itemRequest.getValue(), itemRequest.getBox());
    }

    public static Item of() {
        return new Item(DEFAULT_SEQ, null,null,null,null);
    }

    public static Item of(int seq, String type, String name, String value, Box box) {
        return new Item(seq, type, name, value, box);
    }

    public static Item of(Long id, int seq, String type, String name, String value, Box box) {
        return new Item(id, seq, type, name, value, box);
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

    public void changeSeq(int seq) { this.seq = seq; }

    public void changeItem(Item item) {
        this.seq = item.seq;
        this.type = item.type;
        this.name = item.name;
        this.value = item.value;
        this.box = item.box;
    }
}
