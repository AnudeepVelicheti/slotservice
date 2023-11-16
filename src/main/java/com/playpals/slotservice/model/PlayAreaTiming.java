package com.playpals.slotservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="play_area_timings")
public class PlayAreaTiming {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "play_area_id")
	private int playAreaId;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "start_time")
	private int startTime;
	
	@Column(name = "end_time")
	private int endTime;
	
}
