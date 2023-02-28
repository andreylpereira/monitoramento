package com.gmaps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmaps.api.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role>findByName(String name);
}
