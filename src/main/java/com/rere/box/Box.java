package com.rere.box;

import com.rere.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Box {
    Long id;
    List<Item> items;
    //Long docid;

    public Box(){}
    public Box(Long id) {
        this.id = id;
    }
    public Box(Long id, List<Item>items) {
        this.id = id;
        this.items=new ArrayList<Item>();
        if(items!=null)
        for (Item item:items
        ) {
            this.items.add(item);
        }
    }

    public Box(List<Item> items) {
        this.items=new ArrayList<Item>();
        for (Item item:items
             ) {
            this.items.add(item);
        }
    }


    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
