package com.rere.tableItem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableItemRepository extends JpaRepository<TableItem, Long> {
    List<TableItem> findByTableRow(Long tableRow);

    List<TableItem> findByTableCol(Long tableCol);

    List<TableItem> findByItemId(Long itemId);

    void deleteByItemId(Long itemId);
}
