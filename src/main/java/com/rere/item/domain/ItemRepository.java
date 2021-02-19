package com.rere.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item>  findByType(String type);

    List<Item> findByName(String Name);

    List<Item> findByBoxId(Long boxId);

    void deleteByBoxId(Long boxId);
}
