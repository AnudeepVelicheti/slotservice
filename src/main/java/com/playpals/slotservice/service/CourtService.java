package com.playpals.slotservice.service;
import java.util.List;

import com.playpals.slotservice.model.Courts;

public interface CourtService {

	List<Courts> getCourtsByPlayArea(int playAreaId,int sportId);
}
