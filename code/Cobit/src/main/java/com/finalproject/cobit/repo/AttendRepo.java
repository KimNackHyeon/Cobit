package com.finalproject.cobit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.Attend;

@Repository
public interface AttendRepo extends JpaRepository<Attend, Long>{
	List<Attend> getUserByEmailAndMonth(String email, Long month);
}