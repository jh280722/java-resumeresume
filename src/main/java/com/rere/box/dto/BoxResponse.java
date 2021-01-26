package com.rere.box.dto;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;

import java.util.List;

public class BoxResponse {
    private Long id;
    private List<Item> items;

    public BoxResponse() {
    }

    public BoxResponse(Long id) {
        this.id = id;
    }

    public BoxResponse(Long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static BoxResponse of(Box box) {
        return new BoxResponse(box.getId(), box.getItems());
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
