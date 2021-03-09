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
    private Long row;

    @Column(nullable = false)
    private Long column;

    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    protected TableItem() {
    }

    private TableItem(Long id, Long row, Long column, String value, Item item) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.value = value;
        this.item = item;
    }

    private TableItem(Long row, Long column, String value, Item item) {
        this(DEFAULT_ID, row, column, value, item);
    }

    public static TableItem of() {
        return new TableItem(DEFAULT_RowColumn, DEFAULT_RowColumn, null, null);
    }

    public static TableItem of(TableItemRequest tableItemRequest) {
        return new TableItem(tableItemRequest.getRow(), tableItemRequest.getColumn(), tableItemRequest.getValue(), tableItemRequest.getItem());
    }

    public static TableItem of(Long row, Long column, String value, Item item) {
        return new TableItem(row, column, value, item);
    }

    public static TableItem of(String value, Item item) {
        return new TableItem(DEFAULT_RowColumn, DEFAULT_RowColumn, value, item);
    }

    public static TableItem of(Long id, Long row, Long column, String value, Item item) {
        return new TableItem(id, row, column, value, item);
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

    public void changeRow(Long row) {
        this.row = row;
    }

    public void changeColumn(Long column) {
        this.column = column;
    }

    public void changeTableItem(TableItem tableItem) {
        this.row = tableItem.row;
        this.column = tableItem.column;
        this.value = tableItem.value;
        this.item = tableItem.item;
    }
}
