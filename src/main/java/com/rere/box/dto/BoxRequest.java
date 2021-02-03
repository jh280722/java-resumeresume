package com.rere.box.dto;

import com.rere.item.domain.Item;

import java.util.List;

public class BoxRequest {
    private String name;
    private List<Item> items;

    public BoxRequest() {
    }

    public BoxRequest(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
