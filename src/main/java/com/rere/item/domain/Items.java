package com.rere.item.domain;

import com.rere.item.dto.ItemResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Items {
    @OneToMany(mappedBy = "box")
    private List<Item> items = new ArrayList<>();

    protected Items() {
    }

    private Items(List<Item> items) {
        this.items = items;
    }

    public static Items of(List<Item> items) {
        return new Items(items);
    }

    public static Items of() {
        return new Items();
    }

    public List<ItemResponse> getItemResponses() {
        return items.stream()
                .sorted(Comparator.comparing(Item::getSeq))
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    public int size() {
        return items.size();
    }

    public void add(Item item) {
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }
}
