package com.rere.document.domain;

import com.rere.box.domain.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByName(String name);
}
