package com.rere.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ItemController {
    private final ItemDao itemDao;
    @Autowired
    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @PostMapping(value = "/items")
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        Item item = new Item(itemRequest.getType(), itemRequest.getName(), itemRequest.getValue());
        Item newItem = itemDao.save(item);
        ItemResponse itemResponse = new ItemResponse(newItem.getId());
        return ResponseEntity.created(URI.create("/items/" + newItem.getId()))
                .body(itemResponse);
    }

    @GetMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponse> showItem(@PathVariable Long id) {
        Item newItem = itemDao.findById(id);
        ItemResponse itemResponse = new ItemResponse(
                newItem.getId(), newItem.getType(), newItem.getName(), newItem.getValue());
        return ResponseEntity.ok().body(itemResponse);
    }

    @PutMapping(value="/items/{id}")
    public ResponseEntity updateItem(@RequestBody ItemRequest itemRequest,@PathVariable Long id){
        Item item = itemDao.findById(id);
        Item newItem = new Item( itemRequest.getType(), itemRequest.getName(), itemRequest.getValue());
        itemDao.update(item, newItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        itemDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
