package com.rere.item.domain;

import java.util.Arrays;

public enum ItemType {
    TEXT("text"), TEXTAREA("textArea"), DATE("date"), IMAGE("image"), PERIOD("period"), TABLE("table"), ERROR("error");

    private String type;

    ItemType(String type) {
        this.type = type;
    }

    public static ItemType getItemType(String type) {
        return Arrays.stream(values())
                .filter(itemType -> itemType.type.equals(type))
                .findFirst()
                .orElse(ERROR);
    }

    public String getType() {
        return type;
    }
}
