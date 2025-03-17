package com.surecost.drugapi.repository;

import com.surecost.drugapi.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Drug entity.
 */
@Repository
public interface DrugRepository extends JpaRepository<Drug, UUID> {
    
    /**
     * Find drugs by manufacturer.
     */
    List<Drug> findByManufacturer(String manufacturer);
    
    /**
     * Find drugs by name (case-insensitive partial match).
     */
    List<Drug> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find drugs with price less than or equal to the specified amount.
     */
    List<Drug> findByPriceLessThanEqual(BigDecimal price);
}
