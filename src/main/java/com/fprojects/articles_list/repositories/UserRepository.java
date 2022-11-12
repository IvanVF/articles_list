package com.fprojects.articles_list.repositories;

import com.fprojects.articles_list.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Get user by email
     */
    UserEntity findByEmail(String email);

    /**
     * Get user by username
     */
    UserEntity findByUsername(String username);
}
