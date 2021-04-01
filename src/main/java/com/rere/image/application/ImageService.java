package com.rere.image.application;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.image.domain.Image;
import com.rere.image.domain.ImageRepository;
import com.rere.image.dto.ImageRequest;
import com.rere.image.dto.ImageResponse;
import com.rere.item.application.ItemService;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository ) {
        this.imageRepository = imageRepository;
    }

    public List<ImageResponse> findAll() {
        return imageRepository.findAll().stream()
                .map(ImageResponse::of)
                .collect(Collectors.toList());
    }

    public ImageResponse findById(Long id) {
        return ImageResponse.of(imageRepository.findById(id).orElse(Image.of()));
    }

    public void updateImage(Long id, Image image) {
        Image updateImage = imageRepository.findById(id).orElse(null);
        updateImage.changeImage(image);
    }

    public void updateName(Long id, String name) {
        Image image = imageRepository.findById(id).orElse(null);
        image.changeName(name);
    }

    public void updateItem(Long id, Item item) {
        Image image = imageRepository.findById(id).orElse(null);
        image.changeItem(item);
    }

    public void deleteById(Long id) {
        Item item = imageRepository.findById(id).orElseThrow(RuntimeException::new).getItem();
        imageRepository.deleteById(id);
    }

    public ImageResponse save(ImageRequest imageRequest) {
        return ImageResponse.of(imageRepository.save(Image.of(imageRequest)));
    }

    public void deleteByItemId(Long itemId) {
        imageRepository.deleteByItemId(itemId);
    }

}
