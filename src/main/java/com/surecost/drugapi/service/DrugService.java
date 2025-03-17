package com.surecost.drugapi.service;

import com.surecost.drugapi.model.Drug;
import com.surecost.drugapi.repository.DrugRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for drug operations.
 */
@Service
public class DrugService {

    private final DrugRepository drugRepository;

    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    /**
     * Create a new drug record.
     */
    @Transactional
    public Drug createDrug(Drug drug) {
        // Generate UUID if not provided
        if (drug.getUid() == null) {
            drug.setUid(UUID.randomUUID());
        }
        return drugRepository.save(drug);
    }

    /**
     * Create multiple drug records in batch.
     */
    @Transactional
    public List<Drug> createDrugs(List<Drug> drugs) {
        // Generate UUID for any drug without one
        drugs.forEach(drug -> {
            if (drug.getUid() == null) {
                drug.setUid(UUID.randomUUID());
            }
        });
        return drugRepository.saveAll(drugs);
    }

    /**
     * Get a drug by its UID.
     */
    public Optional<Drug> getDrugByUid(UUID uid) {
        return drugRepository.findById(uid);
    }

    /**
     * Get all drugs.
     */
    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    /**
     * Get drugs by manufacturer.
     */
    public List<Drug> getDrugsByManufacturer(String manufacturer) {
        return drugRepository.findByManufacturer(manufacturer);
    }

    /**
     * Get drugs by name (partial match).
     */
    public List<Drug> getDrugsByName(String name) {
        return drugRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Get drugs with price less than or equal to a maximum.
     */
    public List<Drug> getDrugsByMaxPrice(BigDecimal maxPrice) {
        return drugRepository.findByPriceLessThanEqual(maxPrice);
    }

    /**
     * Update a drug.
     */
    @Transactional
    public Optional<Drug> updateDrug(UUID uid, Drug updatedDrug) {
        return drugRepository.findById(uid)
                .map(existingDrug -> {
                    existingDrug.setManufacturer(updatedDrug.getManufacturer());
                    existingDrug.setName(updatedDrug.getName());
                    existingDrug.setQuantity(updatedDrug.getQuantity());
                    existingDrug.setPrice(updatedDrug.getPrice());
                    return drugRepository.save(existingDrug);
                });
    }

    /**
     * Delete a drug.
     */
    @Transactional
    public boolean deleteDrug(UUID uid) {
        return drugRepository.findById(uid)
                .map(drug -> {
                    drugRepository.delete(drug);
                    return true;
                })
                .orElse(false);
    }
}