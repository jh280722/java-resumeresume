package com.rere.item.application;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    public static final long DEFAULT_ID = 0L;
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemDao) {
        this.itemRepository = itemDao;
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    public ItemResponse findById(Long id) {
        return ItemResponse.of(itemRepository.findById(id).orElse(Item.of()));
    }

    public void updateItem(Long id, Item item) {
        Item updateItem = itemRepository.findById(id).orElse(null);
        updateItem.changeItem(item);
    }

    public void updateName(Long id, String name) {
        Item item = itemRepository.findById(id).orElse(null);
        item.changeName(name);
    }

    public void updateValue(Long id, String value) {
        Item item = itemRepository.findById(id).orElse(null);
        item.changeValue(value);
    }

    public void updateBox(Long id, Box box) {
        Item item = itemRepository.findById(id).orElse(null);
        item.changeBox(box);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemResponse save(ItemRequest itemRequest) {
        return ItemResponse.of(itemRepository.save(Item.of(itemRequest)));
    }

    public void deleteByBoxId(Long boxId) {
        itemRepository.deleteByBoxId(boxId);
    }

    public void updateSeq(Long id, int seq){
        Item item = itemRepository.findById(id).orElse(null);
        item.changeSeq(seq);

    }
}
