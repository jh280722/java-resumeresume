package com.rere.tableItem.domain;

import com.rere.tableItem.dto.TableItemResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class TableItems {
    @OneToMany(mappedBy = "item")
    private List<TableItem> tableItems = new ArrayList<>();

    protected TableItems() {
    }

    private TableItems(List<TableItem> tableItems) {
        this.tableItems = tableItems;
    }

    public static TableItems of(List<TableItem> tableItems) {
        return new TableItems(tableItems);
    }

    public static TableItems of() {
        return new TableItems();
    }

    public List<TableItemResponse> getTableItemResponses() {
        return tableItems.stream()
                .map(TableItemResponse::of)
                .collect(Collectors.toList());
    }

    public int size() {
        return tableItems.size();
    }

    public void add(TableItem tableItem) {
        tableItems.add(tableItem);
    }

    public void remove(TableItem tableItem) {
        tableItems.remove(tableItem);
    }

    public TableItem findById(Long id) {
        return tableItems.stream()
                .filter(tableItem -> tableItem.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
