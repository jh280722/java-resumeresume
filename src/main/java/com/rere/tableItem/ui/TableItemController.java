package com.rere.tableItem.ui;

import com.rere.tableItem.application.TableItemService;
import com.rere.tableItem.domain.TableItem;
import com.rere.tableItem.dto.TableItemRequest;
import com.rere.tableItem.dto.TableItemResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tableItems")
public class TableItemController {
    private final TableItemService tableItemService;

    public TableItemController(TableItemService tableItemService) {
        this.tableItemService = tableItemService;
    }

    @PostMapping
    public ResponseEntity<TableItemResponse> createItem(@RequestBody TableItemRequest tableItemRequest) {
        TableItemResponse tableItemResponse = tableItemService.save(tableItemRequest);
        return ResponseEntity.created(URI.create("/tableItems/" + tableItemResponse.getId()))
                .body(tableItemResponse);
    }

    @GetMapping
    public ResponseEntity<List<TableItemResponse>> getTableItems() {
        return ResponseEntity.ok().body(tableItemService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TableItemResponse> showTableItem(@PathVariable Long id) {
        return ResponseEntity.ok().body(tableItemService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateTableItem(@RequestBody TableItemRequest tableItemRequest, @PathVariable Long id) {
        tableItemService.updateTableItem(id, TableItem.of(tableItemRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTableItem(@PathVariable Long id) {
        tableItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
