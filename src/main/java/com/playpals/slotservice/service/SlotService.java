package com.playpals.slotservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpals.slotservice.model.Slot;

@Service
public interface SlotService {
	
	List<Slot> getSlotsByPlayArea(int playAreaId);
	
}
