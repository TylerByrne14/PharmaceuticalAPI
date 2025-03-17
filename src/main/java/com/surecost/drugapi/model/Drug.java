package com.surecost.drugapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity class representing drug information.
 */
@Entity
@Table(name = "drugs")
public class Drug {

    @Id
    @Column(updatable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier for the drug")
    private UUID uid;
    
    @NotBlank(message = "Manufacturer is required")
    @Schema(description = "Drug manufacturer", example = "PharmaCorp")
    private String manufacturer;
    
    @NotBlank(message = "Drug name is required")
    @Schema(description = "Name of the drug", example = "Aspirin")
    private String name;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    @Schema(description = "Available quantity", example = "500")
    private Integer quantity;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    @Schema(description = "Drug price", example = "9.99")
    private BigDecimal price;

    // Default constructor
    public Drug() {
    }

    // Parameterized constructor
    public Drug(UUID uid, String manufacturer, String name, Integer quantity, BigDecimal price) {
        this.uid = uid;
        this.manufacturer = manufacturer;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters remain the same
    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}