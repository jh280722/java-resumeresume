package com.rere.item.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.box.domain.Box;
import com.rere.item.dto.ItemRequest;
import com.rere.tableItem.domain.TableItems;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Item {
    public static final long DEFAULT_ID = 0L;
    public static final int DEFAULT_SEQ = 0;
    public static final long DEFAULT_SIZE = 0L;
    public static final String TABLE = "table";
    public static final String DEFAULT_PATH = "";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int seq;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    private String value;

    private Long rowSize;

    private Long colSize;

    private String path;

    @Embedded
    @JsonBackReference(value = "tableItem_item")
    private TableItems tableItems = null;

    @ManyToOne
    @JoinColumn(name = "box_id")
//    @JsonManagedReference(value="item_box")
    private Box box;

    protected Item() {
    }

    private Item(Long id, int seq, String type, String name, String value, Box box, Long rowSize, Long colSize, String path) {
        this.id = id;
        this.seq = seq;
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.path = path;
        if (TABLE.equals(type)) {
            tableItems = TableItems.of();
        }
    }

    private Item(int seq, String type, String name, String value, Box box, Long rowSize, Long colSize, String path) {
        this(DEFAULT_ID, seq, type, name, value, box, rowSize, colSize, path);
    }

    private Item(int seq, String type, String name, String value, Box box) {
        this(DEFAULT_ID, seq, type, name, value, box, DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_PATH);
    }

    public static Item of(ItemRequest itemRequest) {
        return new Item(itemRequest.getSeq(), itemRequest.getType(), itemRequest.getName(), itemRequest.getValue(), itemRequest.getBox());
    }

    public static Item of() {
        return new Item(DEFAULT_SEQ, null, null, null, null, DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_PATH);
    }

    public static Item of(int seq, String type, String name, String value, Box box, Long rowSize, Long colSize, String path) {
        return new Item(seq, type, name, value, box, rowSize, colSize, path);
    }

    public static Item of(Long id, int seq, String type, String name, String value, Box box, Long rowSize, Long colSize, String path) {
        return new Item(id, seq, type, name, value, box, rowSize, colSize, path);
    }

    public String setDefaultName() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public int getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public Box getBox() {
        return box;
    }

    public Long getRowSize() {
        return rowSize;
    }

    public Long getColSize() {
        return colSize;
    }

    public String getPath(){return path;}

    public void setBox(Box box) {
        if (Objects.nonNull(this.box)) {
            this.box.getItems().remove(this);
        }
        this.box = box;
        box.getItems().add(this);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeValue(String value) {
        this.value = value;
    }

    public void changeBox(Box box) {
        this.box = box;
    }

    public void changeSeq(int seq) {
        this.seq = seq;
    }

    public void changeRowSize(long rowSize) {
        this.rowSize = rowSize;
    }

    public void changeColSize(long colSize) {
        this.colSize = colSize;
    }
    public void changePath(String path) {
        this.path = path;
    }
    public void changeItem(Item item) {
        this.seq = item.seq;
        this.type = item.type;
        this.name = item.name;
        this.value = item.value;
        this.box = item.box;
        this.rowSize = item.rowSize;
        this.colSize = item.colSize;
        this.path=item.path;
    }

    public void increaseSeq(int seq) {
        this.seq = seq + 1;
    }

    public void decreaseSeq(int seq) {
        this.seq = seq - 1;
    }
}
