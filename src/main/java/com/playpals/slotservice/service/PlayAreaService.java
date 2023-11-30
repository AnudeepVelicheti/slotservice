package com.playpals.slotservice.service;

import com.playpals.slotservice.model.PlayArea;
import com.playpals.slotservice.pojo.PlayAreaRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlayAreaService {
    public void createPlayArea(PlayAreaRequest playAreaRequest, MultipartFile[] files) throws Exception;

    public PlayArea getPlayAreaById(int id);


    public void updatePlayArea(Integer playAreaId, PlayAreaRequest playAreaRequest,MultipartFile[] files) throws Exception;

    public void deletePlayArea(Integer playAreaId);

    List<PlayArea> getPlayAreasByStatus(String requested);



    List<PlayArea> findByUserName(String userName);

    List<PlayArea> findAll();

    List<PlayArea> findByOwnerId(int id);
}
