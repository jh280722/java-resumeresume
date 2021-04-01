package com.rere.item.dto;

import com.rere.box.domain.Box;

public class ItemRequest {
    private int seq;
    private String type;
    private String name;
    private String value;
    private Box box;
    private Long rowSize;
    private Long colSize;

    public ItemRequest() {
    }

    public ItemRequest(String type, String name, String value, Box box, Long rowSize, Long colSize) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.box = box;
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Box getBox() {
        return box;
    }

    public int getSeq() {
        return seq;
    }

    public Long getRowSize() {
        return rowSize;
    }

    public Long getColSize() {
        return colSize;
    }



    public void initSeq(Box box) {
        this.seq = box.getItems().size();
    }

}
