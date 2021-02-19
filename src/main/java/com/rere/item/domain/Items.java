package com.rere.item.domain;

import com.rere.item.dto.ItemResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Items {
    @OneToMany(mappedBy = "box")
    @OrderBy("seq asc")
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

    public void updateSeq(Long itemId, int updateSeq) {
        Item updateItem = items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);

        if (updateItem == null) {
            throw new IllegalArgumentException("바꿀 Item이 없습니다.");
        }

        int originSeq = updateItem.getSeq();

        items.stream()
                .filter(item -> item.getSeq() > originSeq)
                .forEach(item -> item.decreaseSeq(item.getSeq()));

        items.stream()
                .filter(item -> item.getSeq() >= updateSeq)
                .forEach(item -> item.increaseSeq(item.getSeq()));

        updateItem.changeSeq(updateSeq);
    }

    public Item findBySeq(int seq) {
        return items.stream()
                .filter(item -> item.getSeq() == seq)
                .findFirst()
                .orElse(null);

    }

    public Item findById(Long id) {
        return items.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void relocationItems(Long id) {
        Item deletedItem = findById(id);

        items.stream()
                .filter(item -> item.getSeq() > deletedItem.getSeq())
                .forEach(item -> item.decreaseSeq(item.getSeq()));

        remove(deletedItem);
    }
}
