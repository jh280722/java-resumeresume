package com.rere.item.domain;

import com.rere.item.dto.ItemRequest;

public class Item {
    public static final long DEFAULT_ID = 0L;
    Long id;
    String type;
    String name;
    String value;
    Long boxId;
    private Item(){}
    private Item(Long id, String type, String name, String value, Long boxId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.boxId = boxId;
    }

    private Item(String type, String name, String value, Long boxId) {
        this(DEFAULT_ID, type, name, value, boxId);
    }

    public static Item of(ItemRequest itemRequest) {
        return new Item(itemRequest.getType(), itemRequest.getName(), itemRequest.getValue(), itemRequest.getBoxId());
    }

    public static Item of(String type, String name, String value, Long boxId) {
        return new Item(type, name, value, boxId);
    }

    public static Item of(Long id, String type, String name, String value, Long boxId) {
        return new Item(id, type, name, value, boxId);
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

    public Long getBoxId() {
        return boxId;
    }
}
