package com.fprojects.articles_list.repositories;

import com.fprojects.articles_list.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Get role entity by role name
     */
    RoleEntity findByName(String name);
}
