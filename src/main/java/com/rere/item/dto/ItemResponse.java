package com.rere.item.dto;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;

public class ItemResponse {
    private Long id;
    private int seq;
    private String type;
    private String name;
    private String value;
    private Box box;
    private Long rowSize;
    private Long colSize;

    public ItemResponse() {
    }

    public ItemResponse(Long id, int seq, String type, String name, String value, Box box, Long rowSize, Long colSize) {
        this.id = id;
        this.seq = seq;
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getSeq(), item.getType(), item.getName(), item.getValue(), item.getBox(), item.getRowSize(), item.getColSize());
    }

    public Long getId() {
        return id;
    }

    public int getSeq() {
        return seq;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Long getRowSize() {
        return rowSize;
    }

    public Long getColSize() {
        return colSize;
    }


    public Box getBox() {
        return box;
    }
}
