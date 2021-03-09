package com.rere.tableItem.dto;


import com.rere.item.domain.Item;

public class TableItemRequest {

    private Long row;
    private Long column;
    private String value;
    private Item item;

    public TableItemRequest() {
    }

    public TableItemRequest(Long row, Long column, String value, Item item) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.item = item;
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
