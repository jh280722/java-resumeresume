package com.rere.box.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Long> {
    Box findByName(String name);

    void deleteByDocumentId(Long documentId);

    List<Box> findByDocumentId(Long id);
}
