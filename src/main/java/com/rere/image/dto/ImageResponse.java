package com.rere.image.dto;

import com.rere.box.domain.Box;
import com.rere.image.domain.Image;
import com.rere.item.domain.Item;

public class ImageResponse {
    private Long id;
    private String name;
    private Long width;
    private Long height;
    private String path;
    private String description;
    private Item item;

    public ImageResponse() {
    }

    public ImageResponse(Long id, String name, Long width, Long height, String path, String description,Item item) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.path = path;
        this.description = description;
        this.item = item;
    }

    public static ImageResponse of(Image image) {
        return new ImageResponse(image.getId(), image.getName(), image.getWidth(), image.getHeight(), image.getPath(), image.getDescription(), image.getItem());
    }

    public Long getId() {
        return id;
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
