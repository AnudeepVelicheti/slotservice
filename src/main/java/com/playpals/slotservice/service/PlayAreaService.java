package com.playpals.slotservice.service;

import com.playpals.slotservice.pojo.PlayAreaRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PlayAreaService {
    public void createPlayArea(PlayAreaRequest playAreaRequest, MultipartFile file) throws Exception;

    }
