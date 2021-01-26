package com.rere.item.dto;

public class ItemRequest {
    private String type;
    private String name;
    private String value;
    private Long boxId;

    public ItemRequest() {
    }

    public ItemRequest(String type, String name, String value, Long boxId) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.boxId = boxId;
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

    public Long getBoxId() {
        return boxId;
    }
}
