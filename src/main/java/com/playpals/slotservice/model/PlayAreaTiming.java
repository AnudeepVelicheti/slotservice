package com.playpals.slotservice.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="play_area_timings")
public class PlayAreaTiming {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy as needed
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayAreaId() {
		return playAreaId;
	}

	public void setPlayAreaId(int playAreaId) {
		this.playAreaId = playAreaId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
