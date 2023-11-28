package com.playpals.slotservice.service;

import com.playpals.slotservice.model.Details;
import com.playpals.slotservice.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailsServiceImpl implements DetailsService {
    @Autowired
    private DetailsRepository detailsRepository;

    public List<Details> getAllDetails() {
        return detailsRepository.findAll();
    }

    public Optional<Details> getDetailsById(Long id) {
        return detailsRepository.findById(id);
    }

    public Details saveDetails(Details details) {
        return detailsRepository.save(details);
    }

    public void deleteDetails(Long id) {
        detailsRepository.deleteById(id);
    }
}
