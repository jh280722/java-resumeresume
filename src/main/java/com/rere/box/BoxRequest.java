package com.rere.box;

import com.rere.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BoxRequest {
    private List<Item> items;

    public BoxRequest() {
    }

    public BoxRequest(List<Item>items) {
        this.items=new ArrayList<Item>();
        for (Item item:items
             ) {
            this.items.add(item);
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
