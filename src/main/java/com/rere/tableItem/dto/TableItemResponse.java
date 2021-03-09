package com.rere.tableItem.dto;

import com.rere.item.domain.Item;
import com.rere.tableItem.domain.TableItem;

public class TableItemResponse {
    private Long id;
    private Long row;
    private Long column;
    private String value;
    private Item item;

    public TableItemResponse() {
    }

    public TableItemResponse(Long id, Long row, Long column, String value, Item item) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.value = value;
        this.item = item;
    }

    public static TableItemResponse of(TableItem tableItem) {
        return new TableItemResponse(tableItem.getId(), tableItem.getRow(), tableItem.getColumn(), tableItem.getValue(), tableItem.getItem());
    }

    public Long getId() {
        return id;
    }

    public Long getRow() {
        return row;
    }

    public Long getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }

    public Item getItem() {
        return item;
    }
}
