package com.ianhearne.dungeonnotes.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="worlds")
public class World {
	////	ATTRIBUTES    ////
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator_id")
	private User creator;
	
	@OneToMany(mappedBy="hostWorld", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<PointOfInterest> poiList;
	
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
	private void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	private void onUpdated() {
		this.updatedAt = new Date();
	}
	
	////	CONSTRUCTORS    ////
	
	public World() {}
	
	////	GETTERS AND SETTERS    ////

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PointOfInterest> getPoiList() {
		return poiList;
	}

	public void setPoiList(List<PointOfInterest> poiList) {
		this.poiList = poiList;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}
