package com.rere.tableItem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableItemRepository extends JpaRepository<TableItem, Long> {
    List<TableItem> findByRow(Long row);

    List<TableItem> findByColumn(Long row);

    List<TableItem> findByItemId(Long tableId);

    void deleteByItemId(Long itemId);
}
