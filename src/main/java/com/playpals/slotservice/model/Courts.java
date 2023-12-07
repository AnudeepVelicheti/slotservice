package com.playpals.slotservice.model;

import javax.persistence.*;


@Entity
@Table(name="play_area_courts")
public class Courts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="sport_id")
	private Integer sportId;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(int sportId) {
		this.sportId = sportId;
	}

	public int getPlayAreaId() {
		return playAreaId;
	}

	public void setPlayAreaId(int playAreaId) {
		this.playAreaId = playAreaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="play_area_id")
	private int playAreaId;
	
	@Column(name="name")
	private String name;
	
	
}
