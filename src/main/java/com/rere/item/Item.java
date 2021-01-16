package com.rere.item;

public class Item {
    Long id;
    String type;
    String name;
    String value;

    public Item(Long id, String type, String name, String value) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Item(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
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
}
