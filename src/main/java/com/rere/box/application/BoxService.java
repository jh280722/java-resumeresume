package com.rere.box.application;

import com.rere.box.dao.BoxDao;
import com.rere.box.domain.Box;
import com.rere.box.dto.BoxRequest;
import com.rere.box.dto.BoxResponse;
import com.rere.item.application.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxService {
    private final BoxDao boxDao;
    private final ItemService itemService;

    public BoxService(BoxDao boxDao, ItemService itemService) {
        this.boxDao = boxDao;
        this.itemService = itemService;
    }

    public List<BoxResponse> getBoxes() {
        return boxDao.findAll().stream()
                .map(BoxResponse::of)
                .collect(Collectors.toList());
    }

    public BoxResponse findById(Long id) {
        return BoxResponse.of(boxDao.findById(id));
    }

    public void update(Long id, BoxRequest boxRequest) {
        boxDao.update(id, Box.of(boxRequest));
    }

    @Transactional
    public void deleteById(Long id) {
        boxDao.deleteById(id);
        itemService.deletByBoxId(id);
    }

    public BoxResponse save(BoxRequest boxRequest) {
        return BoxResponse.of(boxDao.save(Box.of(boxRequest)));
    }
}
