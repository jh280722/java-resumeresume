package com.rere.item;

public class Item {
    Long id;
    String type;
    String name;
    String value;
    Long boxId;

    public Item(){

    }
    public Item(Long id, String type, String name, String value,Long boxId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.boxId=boxId;
    }

    public Item(String type, String name, String value,Long boxId) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.boxId=boxId;
    }

    public String setDefaultName() {
        return type;
    }

    public Long getId() {
        return id;
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

    public Long getBoxId() {
        return boxId;
    }
}
