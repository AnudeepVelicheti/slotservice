package com.playpals.slotservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "slots")
public class Slot {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "start_time")
	private int startTime;
	
	@Column(name = "end_time")
	private int endTime;
	
}
