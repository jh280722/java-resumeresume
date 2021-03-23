package com.rere.image.domain;

import com.rere.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByName(String Name);

    List<Image> findByItemId(Long boxId);

    void deleteByItemId(Long boxId);
}
