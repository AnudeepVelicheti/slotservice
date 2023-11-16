package com.playpals.slotservice.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playpals.slotservice.model.PlayAreaTiming;
import com.playpals.slotservice.model.Slot;
import com.playpals.slotservice.repository.PlayAreaTimingRepository;
import com.playpals.slotservice.repository.SlotRepository;

@Component
public class SlotServiceImpl implements SlotService{

	@Autowired
	SlotRepository slotRepo;
	
	@Autowired
	PlayAreaTimingRepository playAreaTimingRepo;
	
	@Override
	public List<Slot> getSlotsByPlayArea(int playAreaId,int courtId) {
		List<Slot> resp=new ArrayList<>();
		int now = LocalDate.now().getDayOfWeek();
		String startTime="";
		String today="";
		switch (now) {
		case 1:
			today="Monday";
			break;
		case 2:
			today="Tuesday";
			break;
		case 3:
			today="Wednesday";
			break;
		case 4:
			today="Thursday";
			break;	
		case 5:
			today="Friday";
			break;
		case 6:
			today="Saturday";
			break;
		case 7:
			today="Sunday";
			break;		

		default:
			break;
		}
		System.out.println(""+today+"|"+playAreaId);
		PlayAreaTiming playAreaTiming=playAreaTimingRepo.getSlotsByDayAndPlayArea(today, playAreaId).get();
		int hour=LocalTime.now().getHourOfDay();
		resp=slotRepo.findSlotsByTime(courtId,playAreaId,hour, Integer.parseInt(playAreaTiming.getEndTime()));
		
		return resp;
	}

}
