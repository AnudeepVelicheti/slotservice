package com.playpals.slotservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.playpals.slotservice.model.Courts;
import com.playpals.slotservice.model.Slot;
import com.playpals.slotservice.service.SlotService;

public class SlotController {
	
	@Autowired
	SlotService slotService;
	
	@GetMapping("/api/getSlotsByPlayAreaAndCourt")
	public ResponseEntity<List<Slot>> getSlotsByPlayArea(@RequestParam("playAreaId") int playAreaId,@RequestParam("courtId") int courtId)
	{
		
		List<Slot> response=new ArrayList<>();
		response=slotService.getSlotsByPlayArea(playAreaId);
		return new ResponseEntity<List<Slot>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/api/getCourtByPlayArea")
	public ResponseEntity<List<Courts>> getCourtsByPlayArea(@RequestParam("") int playAreaId)
	{
		return null;
	}
}
