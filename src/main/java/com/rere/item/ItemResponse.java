package com.rere.item;

public class ItemResponse {
    private Long id;
    private String type;
    private String name;
    private String value;

    public ItemResponse() {
    }

    public ItemResponse(Long id) {
        this.id = id;
    }

    public ItemResponse(Long id, String type, String name, String value) {
        this.id = id;
        this.type = type;
        this.name =name;
        this.value = value;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getType(), item.getName(), item.getValue());
    }

    public Long getId() {
        return id;
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
}
