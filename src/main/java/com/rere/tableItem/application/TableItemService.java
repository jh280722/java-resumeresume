package com.rere.tableItem.application;

import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.tableItem.domain.TableItem;
import com.rere.tableItem.domain.TableItemRepository;
import com.rere.tableItem.dto.TableItemRequest;
import com.rere.tableItem.dto.TableItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableItemService {
    private final TableItemRepository tableItemRepository;
    private final ItemRepository itemRepository;

    public TableItemService(TableItemRepository tableItemRepository, ItemRepository itemRepository) {
        this.tableItemRepository = tableItemRepository;
        this.itemRepository = itemRepository;
    }

    public List<TableItemResponse> findAll() {
        return tableItemRepository.findAll().stream()
                .map(TableItemResponse::of)
                .collect(Collectors.toList());
    }

    public TableItemResponse findById(Long id) {
        return TableItemResponse.of(tableItemRepository.findById(id).orElse(TableItem.of()));
    }

    public void updateTableItem(Long id, TableItem tableItem) {
        TableItem updateTableItem = tableItemRepository.findById(id).orElse(null);
        updateTableItem.changeTableItem(tableItem);
    }

    public void updateTableRow(Long id, Long tableRow) {
        TableItem tableitem = tableItemRepository.findById(id).orElse(null);
        tableitem.changeTableRow(tableRow);
    }

    public void updateTableCol(Long id, Long tableCol) {
        TableItem tableitem = tableItemRepository.findById(id).orElse(null);
        tableitem.changeTableCol(tableCol);
    }

    public void updateValue(Long id, String value) {
        TableItem tableItem = tableItemRepository.findById(id).orElse(null);
        tableItem.changeValue(value);
    }

    public void updateItem(Long id, Item item) {
        TableItem tableItem = tableItemRepository.findById(id).orElse(null);
        tableItem.changeItem(item);
    }

    public void deleteById(Long id) {
        Item item = tableItemRepository.findById(id).orElseThrow(RuntimeException::new).getItem();
        tableItemRepository.deleteById(id);
    }

    public TableItemResponse save(TableItemRequest tableItemRequest) {
        return TableItemResponse.of(tableItemRepository.save(TableItem.of(tableItemRequest)));
    }

    public void deleteByItemId(Long tableId) {
        tableItemRepository.deleteByItemId(tableId);
    }

}
