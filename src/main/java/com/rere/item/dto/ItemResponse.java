package com.rere.item.dto;

import com.rere.item.domain.Item;

public class ItemResponse {
    private Long id;
    private String type;
    private String name;
    private String value;
    private Long boxId;

    public ItemResponse() {
    }

    public ItemResponse(Long id) {
        this.id = id;
    }

    public ItemResponse(Long id, String type, String name, String value, Long boxId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.boxId = boxId;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getType(), item.getName(), item.getValue(), item.getBoxId());
    }

    public Long getId() {
        return id;
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

    public Long getBoxId() {
        return boxId;
    }
}
