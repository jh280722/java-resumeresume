package com.rere.image.domain;

import com.rere.image.dto.ImageResponse;
import com.rere.item.domain.Item;
import com.rere.item.dto.ItemResponse;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Images {
    @OneToMany(mappedBy = "item")
    private List<Image> images = new ArrayList<>();

    protected Images() {
    }

    private Images(List<Image> images) {
        this.images = images;
    }

    public static Images of(List<Image> images) {
        return new Images(images);
    }

    public static Images of() {
        return new Images();
    }

    public List<ImageResponse> getImageResponses() {
        return images.stream()
                .map(ImageResponse::of)
                .collect(Collectors.toList());
    }

    public int size() {
        return images.size();
    }

    public void add(Image image) {
        images.add(image);
    }

    public void remove(Image image) {
        images.remove(image);
    }


}
