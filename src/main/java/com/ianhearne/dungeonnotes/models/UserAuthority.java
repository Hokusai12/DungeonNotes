package com.ianhearne.dungeonnotes.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="authorities")
public class UserAuthority {
	
	@Id
	@NotEmpty
	private Long id;
	
	@NotEmpty
	private String authority;
	
	@OneToMany(mappedBy="authority", fetch=FetchType.LAZY)
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
