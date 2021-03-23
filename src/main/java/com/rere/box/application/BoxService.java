package com.rere.box.application;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.box.dto.BoxRequest;
import com.rere.box.dto.BoxResponse;
import com.rere.item.application.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxService {
    private final BoxRepository boxRepository;
    private final ItemService itemService;

    public BoxService(BoxRepository boxRepository, ItemService itemService) {
        this.boxRepository = boxRepository;
        this.itemService = itemService;
    }

    public List<BoxResponse> getBoxes() {
        return boxRepository.findAll().stream()
                .map(BoxResponse::of)
                .collect(Collectors.toList());
    }

    public BoxResponse findById(Long id) {
        return BoxResponse.of(boxRepository.findById(id).orElse(Box.of()));
    }

    public void updateName(Long id, BoxRequest boxRequest) {
        Box box = boxRepository.findById(id).orElse(Box.of());
        box.changeName(boxRequest.getName());
    }

    @Transactional
    public void deleteById(Long id) {
        itemService.deleteByBoxId(id);
        boxRepository.deleteById(id);
    }

    @Transactional
    public void deleteByDocumentId(Long id) {
        List<Box> Box = boxRepository.findByDocumentId(id);
        for (Box box : Box) {
            itemService.deleteByBoxId(box.getId());
        }
        boxRepository.deleteByDocumentId(id);
    }

    public BoxResponse save(BoxRequest boxRequest) {
        return BoxResponse.of(boxRepository.save(Box.of(boxRequest.getName(), boxRequest.getDocument())));
    }

    @Transactional
    public BoxResponse changeItemSeq(Long id, Long itemId, int seq) {
        boxRepository.findById(id)
                .orElse(Box.of())
                .getItems()
                .updateSeq(itemId, seq);
        return BoxResponse.of(boxRepository.findById(id).orElse(Box.of()));
    }
}
