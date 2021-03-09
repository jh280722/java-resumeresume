package com.rere.document.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByName(String name);

    void deleteBySortationId(Long sortationId);

    List<Long> findBySortationId(Long id);
}
