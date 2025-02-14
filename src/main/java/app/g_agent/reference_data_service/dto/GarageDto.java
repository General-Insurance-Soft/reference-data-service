package app.g_agent.reference_data_service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	private LocalDateTime updatedAt;

	@JsonProperty(value = "updated_by")
	private Long updatedBy;

    // Getters and Setters

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }   

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }   

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