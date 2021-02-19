package com.rere.item.dto;

import com.rere.box.domain.Box;

public class ItemRequest {
    private int seq;
    private String type;
    private String name;
    private String value;
    private Box box;

    public ItemRequest() {
    }

    public ItemRequest(String type, String name, String value, Box box) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Box getBox() {
        return box;
    }

    public int getSeq() {
        return seq;
    }

    public void initSeq(Box box) {
        this.seq = box.getItems().size();
    }

}
