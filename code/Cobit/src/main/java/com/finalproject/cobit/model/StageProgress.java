package com.finalproject.cobit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class StageProgress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long userId;

	@Column
	private Long stageId;

	@Column
	private Long star;

	public StageProgress() {
	}

	public StageProgress(Long id, Long userId, Long stageId, Long star) {
		super();
		this.id = id;
		this.userId = userId;
		this.stageId = stageId;
		this.star = star;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getStar() {
		return star;
	}

	public void setStar(Long star) {
		this.star = star;
	}

	@Override
	public String toString() {
		return "StageProgress [userId=" + userId + ", stageId=" + stageId + ", star=" + star + "]";
	}

}
