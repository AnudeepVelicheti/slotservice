package com.playpals.slotservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.playpals.slotservice.exception.ResourceNotFoundException;
import com.playpals.slotservice.model.*;
import com.playpals.slotservice.pojo.PlayAreaRequest;
import com.playpals.slotservice.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Optional;


@Service
public class PlayAreaServiceImpl implements PlayAreaService {


    @Autowired
    private PlayAreaRepository playAreaRepository;
    @Autowired
    private PlayAreaDocRepository playAreaDocRepository;
    @Autowired
    private PlayAreaSportRepository playAreaSportRepository;
    @Autowired
    private PlayAreaTimingRepository playAreaTimingRepository;

    @Autowired
    private CourtRepository courtsRepository;

    @Autowired
    private SlotRepository slotRepository;


    @Autowired
    private SportsRepository sportsRepository;
    
    @Value("${spring.aws.cloudfront}")
    private String cloudfront;

    // Add any other required repositories

    public void createPlayArea(PlayAreaRequest playAreaRequest,MultipartFile[] files) throws Exception {
        // Step 1: Create and save the PlayArea entity
        PlayArea playArea = new PlayArea();
        // Set properties from PlayAreaRequest to PlayArea entity
        playArea.setName(playAreaRequest.getName());
        playArea.setCity(playAreaRequest.getCity());
        playArea.setOwner(playAreaRequest.getOwner());
        playArea.setAddress1(playAreaRequest.getAddress1());
        playArea.setAddress2(playAreaRequest.getAddress2());
        playArea.setState(playAreaRequest.getState());
        playArea.setCountry(playAreaRequest.getCountry());
        playArea.setZipcode(playAreaRequest.getZipcode());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(playAreaRequest);

// Set the JSON string in the playArea
        playArea.setRequest(jsonRequest);

        playArea.setStatus("Requested");

        playArea = playAreaRepository.save(playArea);
        Integer newPlayAreaId = playArea.getId();

        for (MultipartFile file : files) {
            // Process and save each file
            if (file != null && !file.isEmpty()) {
                String fileUrl = uploadFileToS3(file);
                PlayAreaDoc playAreaDoc = new PlayAreaDoc();
                playAreaDoc.setPlayAreaId(newPlayAreaId);
                System.out.println("fileurl  "+fileUrl);
                playAreaDoc.setS3Url(fileUrl);
                playAreaDoc.setName(playArea.getName());
                playAreaDoc.setType(getFileExtension(file));
                System.out.println("file type  "+getFileExtension(file));


                System.out.println("playAreaDoc  "+playAreaDoc);

                playAreaDocRepository.save(playAreaDoc);
            }
            System.out.println("play area doc inserted");

        }
        String timingString = playAreaRequest.getTimings();
        String[] timingParts = timingString.split(" ");

// Assuming a valid format like "Monday 10:00 to 12:00"
        String day = timingParts[0];
//            String[] timeRange = timingParts[2].split(" to ");
        int startTime = Integer.parseInt(timingParts[1].split(":")[0]);
        int endTime = Integer.parseInt(timingParts[3].split(":")[0]);





        // Check if a record with the given playAreaId already exists
        List<PlayAreaTiming> existingTimings = playAreaTimingRepository.findByPlayAreaIdAndDay(newPlayAreaId, day);

        playAreaTimingRepository.deleteAll(existingTimings);
        PlayAreaTiming newPlayAreaTiming = new PlayAreaTiming();
        newPlayAreaTiming.setPlayAreaId(newPlayAreaId);
        newPlayAreaTiming.setDay(day);
        newPlayAreaTiming.setStartTime(startTime);
        newPlayAreaTiming.setEndTime(endTime);
        playAreaTimingRepository.save(newPlayAreaTiming);

        System.out.println("play area timings updated");



        try {
            List<String> sports = playAreaRequest.getSports();
            int numberOfCourts = playAreaRequest.getCourts();
            courtsRepository.deleteByPlayAreaId(newPlayAreaId);
            playAreaSportRepository.deleteByPlayAreaId(newPlayAreaId);

            if (!sports.isEmpty()) {
                for (String sportName : sports) {  // Rename the variable to avoid conflicts
                    PlayAreaSport playAreaSport = new PlayAreaSport();
                    playAreaSport.setPlayAreaId(newPlayAreaId);

                    Sport sport = sportsRepository.getByName(sportName);  // Rename the variable

                    playAreaSport.setSportId(sport.getId());
                    boolean exists = playAreaSportRepository.existsByPlayAreaIdAndSportId(newPlayAreaId, sport.getId());

                    if (!exists) {
                        // Save the new entry only if it doesn't exist
                        playAreaSportRepository.save(playAreaSport);
                        System.out.println("play Area Sport Inserted");
                    }
                    System.out.println(numberOfCourts);

//                    for (int i = 1; i <= numberOfCourts; i++) {
//                        String courtName = "court " + i;
//
//                        // Check if a court with the same name, play area ID, and sport ID already exists
//                        boolean courtExists = courtsRepository.existsByPlayAreaIdAndSportIdAndName(newPlayAreaId, sport.getId(), courtName);
//                        Courts court = new Courts();
//
//                        if (!courtExists) {
//                            // Create a Courts entity and save it
//                            court.setPlayAreaId(newPlayAreaId);
//                            court.setSportId(sport.getId());
//                            court.setName(courtName);
//
//                            courtsRepository.save(court);
//                            System.out.println("Court Inserted: " + courtName);
//                        } else {
//                            System.out.println("Court already exists: " + courtName);
//                        }
//                    }

                }
            }  else {
                throw new EntityNotFoundException("No sports found with name: " + sports);
            }
        } catch (Exception ex) {
            // Handle unexpected exceptions or log them appropriately
            // logger.error("An unexpected error occurred", ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
    }

    // Update PlayArea Service Method
    public void updatePlayArea(Integer playAreaId, PlayAreaRequest playAreaRequest,MultipartFile[] files) throws Exception {
        // Step 1: Retrieve existing PlayArea entity
        Optional<PlayArea> optionalPlayArea = playAreaRepository.findById(playAreaId);
        if (optionalPlayArea.isPresent()) {
            PlayArea playArea = optionalPlayArea.get();

            System.out.println("update playArea");

            // Step 2: Update the PlayArea entity with new values
            playArea.setName(playAreaRequest.getName());
            playArea.setCity(playAreaRequest.getCity());
            playArea.setOwner(playAreaRequest.getOwner());
            playArea.setAddress1(playAreaRequest.getAddress1());
            playArea.setAddress2(playAreaRequest.getAddress2());
            playArea.setState(playAreaRequest.getState());
            playArea.setCountry(playAreaRequest.getCountry());
            playArea.setZipcode(playAreaRequest.getZipcode());
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(playAreaRequest);

// Set the JSON string in the playArea
            playArea.setRequest(jsonRequest);


            // Update other fields as needed

            playAreaRepository.save(playArea); // Save the updated PlayArea entity



            for (MultipartFile file : files) {
                // Process and save each file
                if (file != null && !file.isEmpty()) {
                    String fileUrl = uploadFileToS3(file);
                    PlayAreaDoc playAreaDoc = new PlayAreaDoc();
                    playAreaDoc.setPlayAreaId(playAreaId);
                    System.out.println("fileurl  "+fileUrl);
                    playAreaDoc.setS3Url(fileUrl);
                    playAreaDoc.setName(playArea.getName());
                    playAreaDoc.setType(getFileExtension(file));
                    System.out.println("file type  "+getFileExtension(file));


                    System.out.println("playAreaDoc  "+playAreaDoc);

                    playAreaDocRepository.save(playAreaDoc);
                }
                System.out.println("play area doc inserted");

            }

            String timingString = playAreaRequest.getTimings();
            String[] timingParts = timingString.split(" ");

// Assuming a valid format like "Monday 10:00 to 12:00"
           String day = timingParts[0];
//            String[] timeRange = timingParts[2].split(" to ");
            int startTime = Integer.parseInt(timingParts[1].split(":")[0]);
            int endTime = Integer.parseInt(timingParts[3].split(":")[0]);

// Check if a record with the given playAreaId and day already exists
            List<PlayAreaTiming> existingTimings = playAreaTimingRepository.findByPlayAreaIdAndDay(playAreaId, day);

// Delete existing entries
            playAreaTimingRepository.deleteAll(existingTimings);

// Insert a new entry
            PlayAreaTiming newPlayAreaTiming = new PlayAreaTiming();
            newPlayAreaTiming.setPlayAreaId(playAreaId);
            newPlayAreaTiming.setDay(day);
            newPlayAreaTiming.setStartTime(startTime);
            newPlayAreaTiming.setEndTime(endTime);
            playAreaTimingRepository.save(newPlayAreaTiming);

            System.out.println("play area timings updated");

            PlayAreaSport playAreaSport = new PlayAreaSport();
            playAreaSport.setPlayAreaId(playAreaId);

            try {
                List<String> sports = playAreaRequest.getSports();

                if (!sports.isEmpty()) {
                    for (String s : sports) {
                        Sport sport = sportsRepository.getByName(s);

                        // Check if the association already exists
                        boolean exists = playAreaSportRepository.existsByPlayAreaIdAndSportId(playAreaId, sport.getId());

                        if (!exists) {
                            // If the association doesn't exist, save the new entry
                            System.out.println("inside exists");
                            playAreaSport.setSportId(sport.getId());
                            playAreaSportRepository.save(playAreaSport);
                        }
                    }
                } else {
                    throw new EntityNotFoundException("No sports found with name: " + sports);
                }
            } catch (EntityNotFoundException ex) {
                // Handle the exception or log it appropriately
                // logger.error("No sports found", ex);
                throw ex;
            } catch (Exception ex) {
                // Handle unexpected exceptions or log them appropriately
                // logger.error("An unexpected error occurred", ex);
                throw new RuntimeException("An unexpected error occurred", ex);
            }

            // Additional logic for updating PlayAreaTiming and PlayAreaSport as needed
        } else {
            throw new EntityNotFoundException("PlayArea not found with ID: " + playAreaId);
        }
    }




    private String uploadFileToS3(MultipartFile file) throws Exception {
        // AWS S3 Bucket details
        String bucketName = "playpal-dev";
        String key = "uploads/" + file.getOriginalFilename(); // or use a custom key

        // AWS Credentials (You should not hardcode these in production code)
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIARTB6N2O4COMCBJBC",
                "5u6dwHZutjLv3owcNd2N5YYkfvsikCQiBqkLoiQd"
        );

        // Create S3 client
        S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.US_EAST_2) // e.g., Region.US_EAST_1
                .build();

        // Upload file to S3
        PutObjectResponse response = s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        // Close the S3 client
        s3.close();

        // Return the file URL (Assuming public access or you can generate a pre-signed URL)
        return "https://" + cloudfront + ".s3." + Region.US_EAST_2 + ".amazonaws.com/" + key;
    }


    public static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        // Check if the file name is not null and contains a period.
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return ""; // No extension found or no file name
        }

        // Get everything after the last period.
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }




    public PlayArea getPlayAreaById(int id) {
        Optional<PlayArea> playAreaOptional = playAreaRepository.findById(id);
        if (playAreaOptional.isPresent()) {
            return playAreaOptional.get();
        } else {
            throw new ResourceNotFoundException("PlayArea not found with id " + id);
        }
    }


    @Transactional
    public void deletePlayArea(Integer playAreaId) {
        try {
            // Step 1: Check if the PlayArea exists
            Optional<PlayArea> playAreaOptional = playAreaRepository.findById(playAreaId);

            if (playAreaOptional.isPresent()) {
                // Step 2: Check and delete associated records in other tables (PlayAreaDoc, PlayAreaTiming, PlayAreaSport)

                // Check and delete PlayAreaDocs
                List<PlayAreaDoc> playAreaDocs = playAreaDocRepository.findByPlayAreaId(playAreaId);
                playAreaDocRepository.deleteAll(playAreaDocs);

                // Check and delete PlayAreaTimings
                List<PlayAreaTiming> playAreaTimings = playAreaTimingRepository.findByPlayAreaId(playAreaId);
                playAreaTimingRepository.deleteAll(playAreaTimings);

                // Check and delete PlayAreaSports
                List<PlayAreaSport> playAreaSports = playAreaSportRepository.findByPlayAreaId(playAreaId);
                playAreaSportRepository.deleteAll(playAreaSports);

                // Step 3: Update foreign key in child table (event_slots)
//                slotRepository.updatePlayAreaIdToNull(playAreaId);

                // Step 4: Delete PlayArea
                playAreaRepository.deleteById(playAreaId);
            } else {
                // Handle the case where the PlayArea with the given ID doesn't exist
                throw new EntityNotFoundException("PlayArea with ID " + playAreaId + " not found.");
            }
        } catch (Exception e) {
            // Handle exceptions as needed
            // For example, log the exception or throw a custom exception
            e.printStackTrace(); // log the exception
            throw new RuntimeException("Error deleting PlayArea: " + e.getMessage());
        }
    }

    public List<PlayArea> getPlayAreasByStatus(String status) {
        return playAreaRepository.findByStatus(status);
    }

    public List<PlayArea> getAllPlayAreas() {
        return playAreaRepository.findAll();
    }



}



