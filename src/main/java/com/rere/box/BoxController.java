package com.rere.box;

import com.rere.item.Item;
import com.rere.item.ItemDao;
import com.rere.item.ItemRequest;
import com.rere.item.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BoxController {
    private final BoxDao boxDao;
    @Autowired
    public BoxController(BoxDao boxDao) {
        this.boxDao = boxDao;
    }

    @PostMapping(value = "/boxes")
    public ResponseEntity<BoxResponse> createBox(@RequestBody BoxRequest boxRequest) {
        Box box = new Box(boxRequest.getItems());
        Box newBox = boxDao.save(box);
        BoxResponse boxResponse = new BoxResponse(newBox.getId());
        return ResponseEntity.created(URI.create("/boxes/" + newBox.getId()))
                .body(boxResponse);
    }

    @GetMapping(value = "/boxes")
    public ResponseEntity<List<BoxResponse>> getBox() {
        List<BoxResponse> boxResponse = boxDao.findAll().stream()
                .map(BoxResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(boxResponse);
    }

    @GetMapping(value = "/boxes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoxResponse> showBox(@PathVariable Long id) {
        Box newBox = boxDao.findById(id);
        BoxResponse boxResponse = new BoxResponse(
                newBox.getId(), newBox.getItems());
        return ResponseEntity.ok().body(boxResponse);
    }

    @PutMapping(value="/boxes/{id}")
    public ResponseEntity updateBox(@RequestBody BoxRequest boxRequest,@PathVariable Long id){
        Box box = boxDao.findById(id);
        Box newBox = new Box( boxRequest.getItems());
        boxDao.update(box, newBox);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/boxes/{id}")
    public ResponseEntity deleteBox(@PathVariable Long id) {
        boxDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
