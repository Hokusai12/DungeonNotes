package com.ianhearne.dungeonnotes.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="character_levels")
public class ClassLevels {
	
	////	ATTRIBUTES    ////
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private DndClass dndClass;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="character_id")
	private Character character;
	
	private Integer levels;
	
	////	CONSTRUCTORS    ////
	
	public ClassLevels() {}
	
	////	GETTERS AND SETTERS    ////

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DndClass getDndClass() {
		return dndClass;
	}

	public void setDndClass(DndClass dndClass) {
		this.dndClass = dndClass;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}
}
