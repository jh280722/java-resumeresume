package com.rere.item.ui;

import com.rere.item.application.ItemService;
import com.rere.item.domain.Item;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.save(itemRequest);
        return ResponseEntity.created(URI.create("/items/" + itemResponse.getId()))
                .body(itemResponse);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems() {
        return ResponseEntity.ok().body(itemService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponse> showItem(@PathVariable Long id) {
        return ResponseEntity.ok().body(itemService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateItem(@RequestBody ItemRequest itemRequest, @PathVariable Long id) {
        itemService.updateItem(id, Item.of(itemRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
