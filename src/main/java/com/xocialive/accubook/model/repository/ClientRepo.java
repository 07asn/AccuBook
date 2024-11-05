package com.xocialive.accubook.model.repository;

import com.xocialive.accubook.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findByUserId(Long userId);
}
