package com.gmaps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmaps.api.models.UserEntity;

public interface UserRepository extends JpaRepository <UserEntity, Integer> {
	
	Optional<UserEntity> findByLogin(String login);
	boolean existsByLogin(String login);

}
