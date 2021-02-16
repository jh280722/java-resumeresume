package com.rere.box.domain;

import com.rere.box.dto.BoxResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Boxes {
    @OneToMany(mappedBy = "document")
    private List<Box> boxes = new ArrayList<>();

    protected Boxes() {
    }

    private Boxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public static Boxes of(List<Box> boxes) {
        return new Boxes(boxes);
    }

    public static Boxes of() {
        return new Boxes();
    }

    public List<BoxResponse> getBoxResponses() {
        return boxes.stream()
                .map(BoxResponse::of)
                .collect(Collectors.toList());
    }

    public int size() {
        return boxes.size();
    }

}
