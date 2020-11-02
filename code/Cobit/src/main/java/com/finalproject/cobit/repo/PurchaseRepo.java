package com.finalproject.cobit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long>{
	List<Purchase> getPurchaseByUserIdAndProductId(Long userId, Long productId);
}