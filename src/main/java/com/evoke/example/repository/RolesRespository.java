package com.evoke.example.repository;

import com.evoke.example.entities.ERole;
import com.evoke.example.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRespository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole eRole);
}
