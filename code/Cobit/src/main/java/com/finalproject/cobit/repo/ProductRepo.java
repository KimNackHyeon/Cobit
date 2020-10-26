package com.finalproject.cobit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
	List<Product> findAll();
}