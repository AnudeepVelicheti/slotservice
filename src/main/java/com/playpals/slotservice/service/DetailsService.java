package com.playpals.slotservice.service;

import com.playpals.slotservice.model.Details;

import java.util.List;
import java.util.Optional;

public interface DetailsService {

    public List<Details> getAllDetails();

    public Optional<Details> getDetailsById(Long id);

    public Details saveDetails(Details details);

    public void deleteDetails(Long id);
}
