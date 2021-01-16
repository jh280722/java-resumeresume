package com.rere.item;

public class ItemRequest {
    private String type;
    private String name;
    private String value;

    public ItemRequest() {
    }

    public ItemRequest(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
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
}
