package com.ianhearne.dungeonnotes.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
 * 		---Articles---
 * Articles are the notes that the user will be taking for their world.
 * For right now the user can write html and css to style and write their articles,
 * might allow other formats later down the road. Markdown or plain text would be good.
 */


@Entity
@Table(name="articles")
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Title required")
	@Size(min=3, max=255, message="Article title must be 3-255 characters")
	private String title;
	
	private String text;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="folder_id")
	private Folder folder;
	
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
	private void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
		if(this.text == null) {
			this.text = "";
		}
	}
	
	@PreUpdate
	private void onUpdate() {
		this.updatedAt = new Date();
	}
	
	//Getters and Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
}
