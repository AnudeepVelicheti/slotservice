package com.playpals.slotservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpals.slotservice.model.Courts;
import com.playpals.slotservice.model.Slot;
import com.playpals.slotservice.service.CourtService;
import com.playpals.slotservice.service.SlotService;

@RestController
public class SlotController { 
	
	@Autowired
	SlotService slotService;
	
	@Autowired
	CourtService courtService;
	
	@GetMapping("/api/getSlotsByPlayAreaAndCourt")
	public ResponseEntity<List<Slot>> getSlotsByPlayArea(@RequestParam("playAreaId") int playAreaId,@RequestParam("courtId") int courtId,@RequestParam("inputDate") String input)
	{		
		List<Slot> response=new ArrayList<>();
		response=slotService.getSlotsByPlayArea(playAreaId,courtId,input);
		return new ResponseEntity<List<Slot>>(response, HttpStatus.OK);
	
	}
	
	@GetMapping("/api/getCourtByPlayArea")
	public ResponseEntity<List<Courts>> getCourtsByPlayArea(@RequestParam("playAreaId") int playAreaId,@RequestParam("sportId") int sportId)
	{
		List<Courts> response= new ArrayList<>();
		response=courtService.getCourtsByPlayArea(playAreaId,sportId);
		return new ResponseEntity<List<Courts>>(response,HttpStatus.OK);
	}
}
