package com.rere.tableItem.domain;

import com.rere.item.domain.Item;
import com.rere.tableItem.dto.TableItemRequest;

import javax.persistence.*;


@Entity
public class TableItem {
    public static final long DEFAULT_ID = 0L;
    public static final long DEFAULT_RowColumn = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tableRow;

    @Column(nullable = false)
    private Long tableCol;

    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    protected TableItem() {
    }

    private TableItem(Long id, Long tableRow, Long tableCol, String value, Item item) {
        this.id = id;
        this.tableRow = tableRow;
        this.tableCol = tableCol;
        this.value = value;
        this.item = item;
    }

    private TableItem(Long tableRow, Long tableCol, String value, Item item) {
        this(DEFAULT_ID, tableRow, tableCol, value, item);
    }

    public static TableItem of() {
        return new TableItem(DEFAULT_RowColumn, DEFAULT_RowColumn, null, null);
    }

    public static TableItem of(TableItemRequest tableItemRequest) {
        return new TableItem(tableItemRequest.getTableRow(), tableItemRequest.getTableCol(), tableItemRequest.getValue(), tableItemRequest.getItem());
    }

    public static TableItem of(Long tableRow, Long tableCol, String value, Item item) {
        return new TableItem(tableRow, tableCol, value, item);
    }

    public static TableItem of(String value, Item item) {
        return new TableItem(DEFAULT_RowColumn, DEFAULT_RowColumn, value, item);
    }

    public static TableItem of(Long id, Long tableRow, Long tableCol, String value, Item item) {
        return new TableItem(id, tableRow, tableCol, value, item);
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

//    public void setBox(Box box) {
//        if (Objects.nonNull(this.box)) {
//            this.box.getItems().remove(this);
//        }
//        this.box = box;
//        box.getItems().add(this);
//    }

    public void changeValue(String value) {
        this.value = value;
    }

    public void changeItem(Item item) {
        this.item = item;
    }

    public void changeTableRow(Long tableRow) {
        this.tableRow = tableRow;
    }

    public void changeTableCol(Long tableCol) {
        this.tableCol = tableCol;
    }

    public void changeTableItem(TableItem tableItem) {
        this.tableRow = tableItem.tableRow;
        this.tableCol = tableItem.tableCol;
        this.value = tableItem.value;
        this.item = tableItem.item;
    }
}
