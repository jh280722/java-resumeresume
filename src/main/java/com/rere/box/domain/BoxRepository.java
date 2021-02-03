package com.rere.box.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Long> {
    Box findByName(String name);
}
