package com.rere.image.ui;

import com.rere.image.application.ImageService;
import com.rere.image.domain.Image;
import com.rere.image.dto.ImageRequest;
import com.rere.image.dto.ImageResponse;
import com.rere.item.application.ItemService;
import com.rere.item.domain.Item;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ImageResponse> createImage(@RequestBody ImageRequest imageRequest) {
        ImageResponse imageResponse = imageService.save(imageRequest);
        return ResponseEntity.created(URI.create("/images/" + imageResponse.getId()))
                .body(imageResponse);
    }

    @GetMapping
    public ResponseEntity<List<ImageResponse>> getImages() {
        return ResponseEntity.ok().body(imageService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageResponse> showImage(@PathVariable Long id) {
        return ResponseEntity.ok().body(imageService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateImage(@RequestBody ImageRequest imageRequest, @PathVariable Long id) {
        imageService.updateImage(id, Image.of(imageRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        imageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
