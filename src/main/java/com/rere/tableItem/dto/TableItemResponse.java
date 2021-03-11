package com.rere.tableItem.dto;

import com.rere.item.domain.Item;
import com.rere.tableItem.domain.TableItem;

public class TableItemResponse {
    private Long id;
    private Long tableRow;
    private Long tableCol;
    private String value;
    private Item item;

    public TableItemResponse() {
    }

    public TableItemResponse(Long id, Long tableRow, Long tableCol, String value, Item item) {
        this.id = id;
        this.tableRow = tableRow;
        this.tableCol = tableCol;
        this.value = value;
        this.item = item;
    }

    public static TableItemResponse of(TableItem tableItem) {
        return new TableItemResponse(tableItem.getId(), tableItem.getTableRow(), tableItem.getTableCol(), tableItem.getValue(), tableItem.getItem());
    }

    public Long getId() {
        return id;
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
