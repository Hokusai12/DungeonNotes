package com.ianhearne.dungeonnotes.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="worlds")
public class World {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="World name is required")
	private String name;
	
	private String description;
	
	@NotNull
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="rootFolder_id")
	private Folder rootFolder;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User creator;
	
	@OneToMany(mappedBy="world", cascade=CascadeType.REMOVE)
	private List<TileMap> tileMaps;
	
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
	private void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	private void onUpdate() {
		this.updatedAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Folder getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(Folder rootFolder) {
		this.rootFolder = rootFolder;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<TileMap> getTileMaps() {
		return tileMaps;
	}

	public void setTileMaps(List<TileMap> tileMaps) {
		this.tileMaps = tileMaps;
	}
}
