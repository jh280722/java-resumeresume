package com.rere.item.dto;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;

public class ItemResponse {
    private Long id;
    private int seq;
    private String type;
    private String name;
    private String value;
    private Box box;

    public ItemResponse() {
    }

    public ItemResponse(Long id, int seq, String type, String name, String value, Box box) {
        this.id = id;
        this.seq = seq;
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getSeq(), item.getType(), item.getName(), item.getValue(), item.getBox());
    }

    public Long getId() {
        return id;
    }

    public int getSeq() {
        return seq;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    public Box getBox() {
        return box;
    }
}
