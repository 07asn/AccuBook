package com.xocialive.accubook.model.repository;

import com.xocialive.accubook.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findByUserId(Long userId);
}
