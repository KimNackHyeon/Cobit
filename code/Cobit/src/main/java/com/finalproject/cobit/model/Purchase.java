package com.finalproject.cobit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long userId;
	
	@Column
	private String color;
	
	@Column
	private String eye;
	
	@Column
	private String eyebrow;
	
	@Column
	private String crown;
	
	@Column
	private String shield;
	
	@Column
	private String shord;

}
