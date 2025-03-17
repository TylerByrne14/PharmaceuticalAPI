package com.surecost.drugapi.service;

import com.surecost.drugapi.model.Drug;
import com.surecost.drugapi.repository.DrugRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DrugService.
 */
class DrugServiceTest {

    @Mock
    private DrugRepository drugRepository;

    @InjectMocks
    private DrugService drugService;
    
    private UUID testUid;
    private Drug testDrug;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testUid = UUID.randomUUID();
        testDrug = new Drug(testUid, "Test Manufacturer", "Test Drug", 100, new BigDecimal("25.99"));
    }

    @Test
    void createDrug_ShouldReturnSavedDrug() {
        // Arrange
        when(drugRepository.save(any(Drug.class))).thenReturn(testDrug);
        
        // Act
        Drug result = drugService.createDrug(testDrug);
        
        // Assert
        assertNotNull(result);
        assertEquals(testUid, result.getUid());
        assertEquals("Test Manufacturer", result.getManufacturer());
        verify(drugRepository).save(any(Drug.class));
    }

    @Test
    void getDrugByUid_WhenDrugExists_ShouldReturnDrug() {
        // Arrange
        when(drugRepository.findById(testUid)).thenReturn(Optional.of(testDrug));
        
        // Act
        Optional<Drug> result = drugService.getDrugByUid(testUid);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(testUid, result.get().getUid());
        verify(drugRepository).findById(testUid);
    }

    @Test
    void getAllDrugs_ShouldReturnAllDrugs() {
        // Arrange
        List<Drug> expected = Arrays.asList(testDrug);
        when(drugRepository.findAll()).thenReturn(expected);
        
        // Act
        List<Drug> result = drugService.getAllDrugs();
        
        // Assert
        assertEquals(1, result.size());
        assertEquals(testUid, result.get(0).getUid());
        verify(drugRepository).findAll();
    }
}