package com.finalproject.cobit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.cobit.model.Stage;
import com.finalproject.cobit.model.StageProgress;

@Repository
public interface StageProgressRepo extends JpaRepository<StageProgress, Long>{
//	List<Stage> findAll();
	List<StageProgress> getStageProgressByUserId(Long userId);
}