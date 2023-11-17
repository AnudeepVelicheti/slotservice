package com.playpals.slotservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playpals.slotservice.model.PlayArea;
import com.playpals.slotservice.pojo.ApiResponse;
import com.playpals.slotservice.pojo.PlayAreaRequest;
import com.playpals.slotservice.service.PlayAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class PlayAreaController {

    @Autowired
    private PlayAreaService playAreaService;


    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(path = "/api/createPlayArea", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> createPlayArea(HttpServletRequest request, @RequestParam("jsondata") String jsondata, @RequestParam("file") MultipartFile file) throws IOException
{

    PlayAreaRequest playAreaRequest = objectMapper.readValue(jsondata, PlayAreaRequest.class);


    com.playpals.slotservice.pojo.ApiResponse response = new com.playpals.slotservice.pojo.ApiResponse();
        try {
            // Call the service method to create play area
            playAreaService.createPlayArea(playAreaRequest, (org.springframework.web.multipart.MultipartFile) file);

            // Set success response
            response.setResult(true);
            response.setStatusCode(200); // HTTP 200 OK
            response.setStatusCodeDescription("OK");
            response.setResponse("Play area created successfully"); // Or any other relevant data
        } catch (Exception e) {
            // Set error response
            response.setResult(false);
            response.setStatusCode(500); // HTTP 500 Internal Server Error
            response.setStatusCodeDescription("Internal Server Error");
            response.setMessage("Error creating play area: " + e.getMessage());
            response.setResponse(null);
        }
        return new ResponseEntity<com.playpals.slotservice.pojo.ApiResponse>(response, HttpStatus.OK);
    }


}
