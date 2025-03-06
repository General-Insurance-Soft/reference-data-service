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

	@NotBlank(message = "Code is required")
	@NotNull(message = "Code is required")
	private String code;

	private String email;

	private String phone;

	private String jurisdiction;

	private String address;

	@JsonProperty(value = "approved_insurance_companies")
	private String approvedInsuranceCompanies; // JSON array of insurer IDs

	@JsonProperty(value = "contact_phone")
	private String contactPhone;

	@JsonProperty(value = "created_at")
	private LocalDateTime createdAt;

	@JsonProperty(value = "updated_at")
	private LocalDateTime updatedAt;

	@JsonProperty(value = "updated_by")
	private Long updatedBy;

	@JsonProperty(value = "organization_id")
	private Long organizationId;

	// Getters and Setters

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

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

	public String getApprovedInsuranceCompanies() {
		return approvedInsuranceCompanies;
	}

	public void setApprovedInsuranceCompanies(String approvedInsuranceCompanies) {
		this.approvedInsuranceCompanies = approvedInsuranceCompanies;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}