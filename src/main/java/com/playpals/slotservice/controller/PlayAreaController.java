package com.playpals.slotservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playpals.slotservice.model.Details;
import com.playpals.slotservice.model.PlayArea;
import com.playpals.slotservice.pojo.ApiResponse;
import com.playpals.slotservice.pojo.PlayAreaPojo;
import com.playpals.slotservice.pojo.PlayAreaRequest;
import com.playpals.slotservice.repository.PlayAreaDocRepository;
import com.playpals.slotservice.repository.PlayAreaRepository;
import com.playpals.slotservice.repository.UserRepository;
import com.playpals.slotservice.service.DetailsServiceImpl;
import com.playpals.slotservice.service.PlayAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.playpals.slotservice.model.PlayAreaDoc;
import com.playpals.slotservice.model.User;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")

public class PlayAreaController {

    @Autowired
    private PlayAreaService playAreaService;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DetailsServiceImpl detailsService;

    @Autowired
    private PlayAreaDocRepository playAreaDocRepository;

    @Autowired
    private PlayAreaRepository playAreaRepository;

    @Autowired
    private UserRepository userRepository;




    @PostMapping(path = "/api/createPlayArea", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> createPlayArea(HttpServletRequest request, @RequestParam String playAreaRequest, @RequestParam("files") MultipartFile[] files) throws IOException
{

    ObjectMapper objectMapper = new ObjectMapper();
    PlayAreaRequest playAreaRequest1 = objectMapper.readValue(playAreaRequest, PlayAreaRequest.class);


    com.playpals.slotservice.pojo.ApiResponse response = new com.playpals.slotservice.pojo.ApiResponse();
        try {
            // Call the service method to create play area
            playAreaService.createPlayArea(playAreaRequest1, files);

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


    // Update PlayArea API
    @PostMapping(path = "/api/updatePlayArea", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> updatePlayArea(
            @RequestParam("playAreaId") Integer playAreaId,
         @RequestParam String playAreaRequest,
//            @RequestParam("files") List<MultipartFile> files
            @RequestParam("files") MultipartFile[] files) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        PlayAreaRequest playAreaRequest1 = objectMapper.readValue(playAreaRequest, PlayAreaRequest.class);



        ApiResponse response=new ApiResponse();
       try {
            playAreaService.updatePlayArea(playAreaId, playAreaRequest1, files);
            response.setResult(true);
            response.setStatusCode(200);
            response.setStatusCodeDescription("OK");
            response.setResponse("Play area updated successfully");
        } catch (Exception e) {
            response.setResult(false);
            response.setStatusCode(500);
            response.setStatusCodeDescription("Internal Server Error");
            response.setMessage("Error updating play area: " + e.getMessage());
            response.setResponse(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public Details updateOrSaveDetails(@PathVariable Long id, @RequestBody Details details) {
        Optional<Details> existingDetails = detailsService.getDetailsById(id);

        if (existingDetails.isPresent()) {
            Details updatedDetails = existingDetails.get();
            updatedDetails.setName(details.getName());
            updatedDetails.setAddress(details.getAddress());
            updatedDetails.setPhone(details.getPhone());
            updatedDetails.setDetails(details.getDetails());
            return detailsService.saveDetails(updatedDetails);
        } else {
            details.setId(id);
            return detailsService.saveDetails(details);
        }
    }



    @GetMapping("/api/playareas/{id}")
    public ResponseEntity<PlayArea> getPlayAreaRequestJson(@PathVariable int id) {
        PlayArea playArea = playAreaService.getPlayAreaById(id); // Casting int to long, assuming id is a long type
        return ResponseEntity.ok(playArea);
    }

    @DeleteMapping("/api/deletePlayArea/{playAreaId}")
    public ResponseEntity<ApiResponse> deletePlayArea(@PathVariable Integer playAreaId) {
        ApiResponse response = new ApiResponse();
        try {
            playAreaService.deletePlayArea(playAreaId);
            response.setResult(true);
            response.setStatusCode(200);
            response.setStatusCodeDescription("OK");
            response.setResponse("Play area deleted successfully");
        } catch (Exception e) {
            response.setResult(false);
            response.setStatusCode(500);
            response.setStatusCodeDescription("Internal Server Error");
            response.setMessage("Error deleting play area: " + e.getMessage());
            response.setResponse(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/getNonRequestedPlayAreas")
    public List<PlayAreaPojo> getNonRequestedPlayAreasWithDocs() {
        List<PlayArea> nonRequestedPlayAreas = playAreaRepository.findAll()
                .stream()
                .filter(playArea -> !playArea.getStatus().equals("Requested"))
                .collect(Collectors.toList());

        return nonRequestedPlayAreas.stream()
                .map(playArea -> {
                    List<String> docUrls = playAreaDocRepository.findByPlayAreaId(playArea.getId())
                            .stream()
                            .map(PlayAreaDoc::getS3Url)
                            .collect(Collectors.toList());
                    PlayAreaPojo playAreaPojo = new PlayAreaPojo();
                    playAreaPojo.setId(playArea.getId());
                    playAreaPojo.setDocUrls(docUrls);
                    playAreaPojo.setCity(playArea.getCity());
                    playAreaPojo.setAddress1(playArea.getAddress1());
                    playAreaPojo.setAddress2(playArea.getAddress2());
                    playAreaPojo.setCountry(playArea.getCountry());
                    playAreaPojo.setComments(playArea.getComments());
                    playAreaPojo.setName(playArea.getName());
                    playAreaPojo.setOwner(playArea.getOwner());
                    playAreaPojo.setStatus(playArea.getStatus());
                    playAreaPojo.setZipcode(playArea.getZipcode());// Set other fields as needed



                    return playAreaPojo;
                })
                .collect(Collectors.toList());
    }


    @GetMapping("/api/requestedPlayArea")
    public List<PlayAreaPojo> getRequestedPlayAreas() {
        List<PlayArea> playAreas = playAreaService.getPlayAreasByStatus("Requested");
        return playAreas.stream()
                .map(playArea -> {
                    List<String> docUrls = playAreaDocRepository.findByPlayAreaId(playArea.getId())
                            .stream()
                            .map(PlayAreaDoc::getS3Url)
                            .collect(Collectors.toList());

                    PlayAreaPojo playAreaPojo = new PlayAreaPojo();
                    playAreaPojo.setId(playArea.getId());
                    playAreaPojo.setDocUrls(docUrls);
                    playAreaPojo.setCity(playArea.getCity());
                    playAreaPojo.setAddress1(playArea.getAddress1());
                    playAreaPojo.setAddress2(playArea.getAddress2());
                    playAreaPojo.setCountry(playArea.getCountry());
                    playAreaPojo.setComments(playArea.getComments());
                    playAreaPojo.setName(playArea.getName());
                    playAreaPojo.setOwner(playArea.getOwner());
                    playAreaPojo.setStatus(playArea.getStatus());
                    playAreaPojo.setZipcode(playArea.getZipcode());// Set other fields as needed

                    return playAreaPojo;
                })
                .collect(Collectors.toList());
    }


//    @GetMapping("/api/getPlayAreas")
//    public List<PlayAreaPojo> getPlayAreas(@RequestParam(value = "userName", required = false) String userName) {
//        List<PlayArea> playAreas;
//
//        if (userName != null && !userName.isEmpty()) {
//            playAreas = playAreaService.findByUserName(userName);
//        } else {
//            playAreas = playAreaService.findAll();
//        }
//
//        return playAreas.stream()
//                .map(this::convertToPlayAreaPojo)
//                .collect(Collectors.toList());
//    }


    @GetMapping("/api/getPlayAreas")
    public List<PlayAreaPojo> getPlayAreas(@RequestParam(value = "userName", required = false) String userName) {
        List<PlayArea> playAreas;

        if (userName != null && !userName.isEmpty()) {
            // Assuming you have a method in your UserService to find a user by username
            User user = userRepository.findByUsername(userName);
            if (user!=null) {
                playAreas = playAreaService.findByOwnerId(user.getId());
            } else {
                // Handle the case where the user is not found
                playAreas = new ArrayList<>();
            }
        } else {
            playAreas = playAreaService.findAll();
        }

        return playAreas.stream()
                .map(this::convertToPlayAreaPojo)
                .collect(Collectors.toList());
    }

    private PlayAreaPojo convertToPlayAreaPojo(PlayArea playArea) {
        List<String> docUrls = playAreaDocRepository.findByPlayAreaId(playArea.getId())
                .stream()
                .map(PlayAreaDoc::getS3Url)
                .collect(Collectors.toList());

        PlayAreaPojo playAreaPojo = new PlayAreaPojo();
        playAreaPojo.setId(playArea.getId());
        playAreaPojo.setDocUrls(docUrls);
        playAreaPojo.setCity(playArea.getCity());
        playAreaPojo.setAddress1(playArea.getAddress1());
        playAreaPojo.setAddress2(playArea.getAddress2());
        playAreaPojo.setCountry(playArea.getCountry());
        playAreaPojo.setComments(playArea.getComments());
        playAreaPojo.setName(playArea.getName());
        playAreaPojo.setOwner(playArea.getOwner());
        playAreaPojo.setStatus(playArea.getStatus());
        playAreaPojo.setZipcode(playArea.getZipcode());// Set other fields as needed

        return playAreaPojo;
    }




}
