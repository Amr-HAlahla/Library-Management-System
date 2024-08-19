package com.amr.training.securitydemo.repository;

import com.amr.training.securitydemo.entity.Role;
import com.amr.training.securitydemo.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
