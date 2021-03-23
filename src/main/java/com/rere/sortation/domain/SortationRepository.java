package com.rere.sortation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SortationRepository extends JpaRepository<Sortation, Long> {
    Sortation findByName(String name);

    void deleteByUserId(Long documentId);

    List<Sortation> findByUserId(Long id);
}
