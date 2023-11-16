package com.playpals.slotservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpals.slotservice.model.PlayAreaTiming;

@Service
public interface PlayAreaTimingService {

	List<PlayAreaTiming> getPlayAreasByTimimg();
	
}
