package app.g_agent.reference_data_service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GarageDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    private String location;

    private String approvedInsuranceCompanies; // JSON array of insurer IDs

    private String contactPhone;

    private LocalDateTime createdAt;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApprovedInsuranceCompanies() {
        return approvedInsuranceCompanies;
    }

    public void setApprovedInsuranceCompanies(String approvedInsuranceCompanies) {
        this.approvedInsuranceCompanies = approvedInsuranceCompanies;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}