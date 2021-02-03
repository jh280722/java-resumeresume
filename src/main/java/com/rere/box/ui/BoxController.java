package com.rere.box.ui;

import com.rere.box.application.BoxService;
import com.rere.box.dto.BoxRequest;
import com.rere.box.dto.BoxResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {
    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping
    public ResponseEntity<BoxResponse> createBox(@RequestBody BoxRequest boxRequest) {
        BoxResponse boxResponse = boxService.save(boxRequest);
        return ResponseEntity.created(URI.create("/boxes/" + boxResponse.getId()))
                .body(boxResponse);
    }

    @GetMapping
    public ResponseEntity<List<BoxResponse>> getBox() {
        return ResponseEntity.ok().body(boxService.getBoxes());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoxResponse> showBox(@PathVariable Long id) {
        return ResponseEntity.ok().body(boxService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateBox(@RequestBody BoxRequest boxRequest, @PathVariable Long id) {
        boxService.updateName(id, boxRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        boxService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
