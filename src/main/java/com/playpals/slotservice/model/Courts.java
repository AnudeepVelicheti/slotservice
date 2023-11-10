package com.playpals.slotservice.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name="play_area-courts")
@Data
public class Courts {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name="sport_id")
	private int sportId;
	
	@Column(name="play_area_id")
	private int playAreaId;
	
	@Column(name="name")
	private String name;
	
	
}
