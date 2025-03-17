package com.surecost.drugapi.controller;

import com.surecost.drugapi.model.Drug;
import com.surecost.drugapi.service.DrugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for drug operations.
 */
@RestController
@RequestMapping("/api/drugs")
@Tag(name = "Drug API", description = "API for managing drug information")
public class DrugController {

    private final DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * Create a new drug.
     */
    @PostMapping
    @Operation(summary = "Create a new drug record", description = "Creates a new drug record with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Drug record created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Drug> createDrug(@Valid @RequestBody Drug drug) {
        Drug createdDrug = drugService.createDrug(drug);
        return new ResponseEntity<>(createdDrug, HttpStatus.CREATED);
    }

    /**
     * Create multiple drugs in batch.
     */
    @PostMapping("/batch")
    @Operation(summary = "Create multiple drug records", description = "Creates multiple drug records in a single batch")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Drug records created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<List<Drug>> createDrugs(@Valid @RequestBody List<Drug> drugs) {
        List<Drug> createdDrugs = drugService.createDrugs(drugs);
        return new ResponseEntity<>(createdDrugs, HttpStatus.CREATED);
    }

    /**
     * Get a drug by its UID.
     */
    @GetMapping("/{uid}")
    @Operation(summary = "Get drug by UID", description = "Retrieves a drug record by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Drug found"),
            @ApiResponse(responseCode = "404", description = "Drug not found")
    })
    public ResponseEntity<Drug> getDrugByUid(
            @Parameter(description = "Drug UID", required = true)
            @PathVariable UUID uid) {
        return drugService.getDrugByUid(uid)
                .map(drug -> new ResponseEntity<>(drug, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get all drugs.
     */
    @GetMapping
    @Operation(summary = "Get all drugs", description = "Retrieves all drug records")
    @ApiResponse(responseCode = "200", description = "List of drugs retrieved")
    public ResponseEntity<List<Drug>> getAllDrugs() {
        List<Drug> drugs = drugService.getAllDrugs();
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    /**
     * Get drugs by manufacturer.
     */
    @GetMapping("/manufacturer/{manufacturer}")
    @Operation(summary = "Get drugs by manufacturer", description = "Retrieves drugs from a specific manufacturer")
    @ApiResponse(responseCode = "200", description = "List of drugs retrieved")
    public ResponseEntity<List<Drug>> getDrugsByManufacturer(
            @Parameter(description = "Manufacturer name", required = true)
            @PathVariable String manufacturer) {
        List<Drug> drugs = drugService.getDrugsByManufacturer(manufacturer);
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    /**
     * Search drugs by name.
     */
    @GetMapping("/search")
    @Operation(summary = "Search drugs by name", description = "Searches for drugs containing the specified name")
    @ApiResponse(responseCode = "200", description = "List of drugs retrieved")
    public ResponseEntity<List<Drug>> searchDrugsByName(
            @Parameter(description = "Drug name (partial match)", required = true)
            @RequestParam String name) {
        List<Drug> drugs = drugService.getDrugsByName(name);
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    /**
     * Get drugs by maximum price.
     */
    @GetMapping("/price/max")
    @Operation(summary = "Get drugs by maximum price", description = "Retrieves drugs with price less than or equal to the specified amount")
    @ApiResponse(responseCode = "200", description = "List of drugs retrieved")
    public ResponseEntity<List<Drug>> getDrugsByMaxPrice(
            @Parameter(description = "Maximum price", required = true)
            @RequestParam BigDecimal price) {
        List<Drug> drugs = drugService.getDrugsByMaxPrice(price);
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    /**
     * Update a drug.
     */
    @PutMapping("/{uid}")
    @Operation(summary = "Update drug", description = "Updates an existing drug record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Drug updated successfully"),
            @ApiResponse(responseCode = "404", description = "Drug not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Drug> updateDrug(
            @Parameter(description = "Drug UID", required = true)
            @PathVariable UUID uid,
            @Valid @RequestBody Drug drug) {
        return drugService.updateDrug(uid, drug)
                .map(updatedDrug -> new ResponseEntity<>(updatedDrug, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a drug.
     */
    @DeleteMapping("/{uid}")
    @Operation(summary = "Delete drug", description = "Deletes a drug record")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Drug deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Drug not found")
    })
    public ResponseEntity<Void> deleteDrug(
            @Parameter(description = "Drug UID", required = true)
            @PathVariable UUID uid) {
        boolean deleted = drugService.deleteDrug(uid);
        return deleted 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}