package com.playpals.slotservice.service;


import com.playpals.slotservice.model.*;
import com.playpals.slotservice.pojo.PlayAreaRequest;
import com.playpals.slotservice.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.TextStyle;


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
    private SportsRepository sportsRepository;

    // Add any other required repositories

    public void createPlayArea(PlayAreaRequest playAreaRequest,MultipartFile file) throws Exception {
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

        playArea = playAreaRepository.save(playArea);
        Integer newPlayAreaId = playArea.getId();



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


        PlayAreaTiming playAreaTiming = new PlayAreaTiming();
        playAreaTiming.setPlayAreaId(newPlayAreaId);
        playAreaTiming.setStartTime(playAreaRequest.getTimings().get(0));
        playAreaTiming.setEndTime(playAreaRequest.getTimings().get(1));
        LocalDate today = LocalDate.now();
        String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH); // For the full name of the day
        playAreaTiming.setDay(dayOfWeek);
        playAreaTimingRepository.save(playAreaTiming);

        System.out.println("play area timing inserted");


        PlayAreaSport playAreaSport=new PlayAreaSport();
        playAreaSport.setPlayAreaId(newPlayAreaId);


        try {
            List<String> sports = playAreaRequest.getSports();


            if (!sports.isEmpty()) {
                for (String s : sports) {
                    Sport sport = sportsRepository.getByName(s);
                    playAreaSport.setSportId(sport.getId());
                    playAreaSportRepository.save(playAreaSport);
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
        return "https://" + bucketName + ".s3." + Region.US_EAST_2 + ".amazonaws.com/" + key;
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


}
