package com.finalproject.cobit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	List<User> findAll();
	Optional<User> getUserByEmail(String email);
}