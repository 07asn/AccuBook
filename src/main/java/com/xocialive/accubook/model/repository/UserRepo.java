package com.xocialive.accubook.model.repository;

import com.xocialive.accubook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
