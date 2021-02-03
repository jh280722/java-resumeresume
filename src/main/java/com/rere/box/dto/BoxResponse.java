package com.rere.box.dto;

import com.rere.box.domain.Box;
import com.rere.item.dto.ItemResponse;

import java.util.List;

public class BoxResponse {
    private Long id;
    private String name;
    private List<ItemResponse> items;

    public BoxResponse() {
    }

    public BoxResponse(Long id, String name, List<ItemResponse> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public static BoxResponse of(Box box) {
        return new BoxResponse(box.getId(), box.getName(), box.getItems().getItemResponses());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ItemResponse> getItems() {
        return items;
    }
}
