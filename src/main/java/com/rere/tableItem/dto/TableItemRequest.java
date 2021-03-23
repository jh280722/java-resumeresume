package com.rere.tableItem.dto;


import com.rere.item.domain.Item;

public class TableItemRequest {

    private Long tableRow;
    private Long tableCol;
    private String value;
    private Item item;

    public TableItemRequest() {
    }

    public TableItemRequest(Long tableRow, Long tableCol, String value, Item item) {
        this.tableRow = tableRow;
        this.tableCol = tableCol;
        this.value = value;
        this.item = item;
    }

    public Long getTableRow() {
        return tableRow;
    }

    public Long getTableCol() {
        return tableCol;
    }

    public String getValue() {
        return value;
    }

    public Item getItem() {
        return item;
    }

}
