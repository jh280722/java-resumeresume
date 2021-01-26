package com.rere.box;

import com.rere.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BoxResponse {
    private Long id;
    private List<Item> items;

    public BoxResponse() {
    }

    public BoxResponse(Long id) {
        this.id = id;
    }

    public BoxResponse(Long id, List<Item>items) {
        this.id = id;
        this.items=new ArrayList<>();
        if(items!=null)
        for (Item item:items
             ) {
            this.items.add(item);
        }
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
