package com.rere.image.dto;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;

import javax.persistence.Column;

public class ImageRequest {
    private String name;
    private Long width;
    private Long height;
    private String path;
    private String description;
    private Item item;

    public ImageRequest() {
    }

    public ImageRequest(String name, Long width, Long height, String path, String description,Item item) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.path = path;
        this.description = description;
        this.item = item;
    }

    public String getName() {
        return name;
    }
    public Long getWidth() {
        return width;
    }
    public Long getHeight() {
        return height;
    }
    public String getPath(){return path;}
    public String getDescription(){return description;}
    public Item getItem() {
        return item;
    }




}
