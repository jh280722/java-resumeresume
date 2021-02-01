package com.rere.item.application;

import com.rere.item.dao.ItemDao;
import com.rere.item.domain.Item;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<ItemResponse> findAll() {
        return itemDao.findAll().stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    public ItemResponse findById(Long id) {
        return ItemResponse.of(itemDao.findById(id));
    }

    public void update(Long id, ItemRequest itemRequest) {
        itemDao.update(id, Item.of(itemRequest));
    }

    public void deleteById(Long id) {
        itemDao.deleteById(id);
    }

    public ItemResponse save(ItemRequest itemRequest) {
        return ItemResponse.of(itemDao.save(Item.of(itemRequest)));
    }

    public void deletByBoxId(Long boxId) {
        itemDao.deletByBoxId(boxId);
    }
}
