package com.rere.box.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rere.box.dto.BoxRequest;
import com.rere.document.domain.Document;
import com.rere.item.domain.Item;
import com.rere.item.domain.Items;

import javax.persistence.*;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    public static final String DEFAULT_NAME = "";
    public static final Long DEFAULT_ID = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Embedded
    @JsonBackReference(value="item_box")
    private Items items = Items.of();

    @ManyToOne
    @JoinColumn(name = "document_id")
//    @JsonManagedReference(value="box_document")
    private Document document;

    protected Box() {
    }

    private Box(Long id,String name,Document document) {
        this.id=id;
        this.name = name;
        this.document=document;
    }
    private  Box(String name,Document document){
        this(DEFAULT_ID,name,document);
    }
    private Box(Document document){
        this(DEFAULT_NAME,document);
    }
    public static Box of() {
        return new Box();
    }



    public static Box of(BoxRequest boxRequest) {
        return new Box(boxRequest.getName(),boxRequest.getDocument());
    }

    public static Box of(String name,Document document) {
        return new Box(name,document);
    }
    public static Box of(Long id,String name,Document document) {
        return new Box(id,name,document);
    }


    public Long getId() {
        return id;
    }

    public Items getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public Document getDocument(){
        return document;
    }

    public void changeName(String name) {
        this.name = name;
    }

}
