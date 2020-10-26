package com.finalproject.cobit.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String email;

	@Column
	private String pw;

	@Column
	private String nickname;

	@Column
	@ColumnDefault("/img/profile_default.png")
	private String image;

	@Column
	@ColumnDefault("0")
	private Long star;

	public User() {
	}

	public User(Long id, String email, String pw, String nickname, String image, Long star) {
		super();
		this.id = id;
		this.email = email;
		this.pw = pw;
		this.nickname = nickname;
		this.image = image;
		this.star = star;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getStar() {
		return star;
	}

	public void setStar(Long star) {
		this.star = star;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", pw=" + pw + ", nickname=" + nickname + ", image=" + image
				+ ", star=" + star + "]";
	};

}
