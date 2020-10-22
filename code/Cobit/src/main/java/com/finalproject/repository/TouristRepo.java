package com.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.TouristDto;


@Repository
public interface TouristRepo extends JpaRepository<TouristDto, Long>{
	public List<TouristDto> findAll();
}