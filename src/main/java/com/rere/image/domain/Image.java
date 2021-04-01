package com.rere.image.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rere.box.domain.Box;
import com.rere.image.dto.ImageRequest;
import com.rere.item.domain.Item;
import com.rere.item.dto.ItemRequest;
import com.rere.tableItem.domain.TableItems;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Image {
    public static final long DEFAULT_ID = 0L;
    public static final long DEFAULT_SIZE = 0L;
    public static final String DEFAULT_PATH = "";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long width;

    private Long height;

    private String path;

    private String description;

    @Embedded
    @JsonBackReference(value = "image_item")
    private TableItems tableItems = null;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    protected Image() {
    }

    private Image(Long id, String name, Long width, Long height, String path, String description,Item item) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.path = path;
        this.description = description;
        this.item = item;
    }

    private Image(String name, Long width, Long height, String path, String description,Item item) {
        this(DEFAULT_ID, name, width, height, path, description,item);
    }

    public static Image of(ImageRequest imageRequest) {
        return new Image(imageRequest.getName(), imageRequest.getWidth(), imageRequest.getHeight(), imageRequest.getPath(), imageRequest.getDescription(),imageRequest.getItem());
    }

    public static Image of() {
        return new Image(DEFAULT_ID, null,DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_PATH, null,null);
    }

    public static Image of(String name, Long width, Long height, String path, String description,Item item) {
        return new Image(name, width, height, path, description,item);
    }

    public static Image of(Long id,String name, Long width, Long height, String path, String description,Item item) {
        return new Image(id, name, width, height, path, description,item);
    }
    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }
    public Long getWidth() { return width;
    }

    public Long getHeight() { return height;
    }

    public String getPath() { return path;
    }
    public String getDescription() { return description;
    }
    public Item getItem(){return item;}

    public void changeName(String name) {
        this.name = name;
    }
    public void changeWidth(Long width) {
        this.width = width;
    }
    public void changeHeight(Long height) {
        this.height = height;
    }

    public void changeItem(Item item) {
        this.item = item;
    }
    public void changePath(String path) {
        this.path = path;
    }
    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeImage(Image image) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.path = path;
        this.description = description;
        this.item = item;
    }

}
