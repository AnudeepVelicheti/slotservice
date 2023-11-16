package com.playpals.slotservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playpals.slotservice.model.Courts;
import com.playpals.slotservice.repository.CourtRepository;

@Component
public class CourtServiceimpl implements CourtService{
	
	@Autowired
	CourtRepository courtRepo;

	@Override
	public List<Courts> getCourtsByPlayArea(int playAreaId,int sportId) {
		List<Courts> resp=new ArrayList<>();
		resp=courtRepo.findCourtsByTime(playAreaId, sportId).get();
		return resp;
	}

}
