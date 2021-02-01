package com.rere.box.dto;

import com.rere.item.domain.Item;

import java.util.List;

public class BoxRequest {
    private List<Item> items;

    public BoxRequest() {
    }

    public BoxRequest(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
