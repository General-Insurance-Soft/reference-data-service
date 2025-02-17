package app.g_agent.reference_data_service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleModelDto {

	private Long id;

	@NotBlank(message = "Model is required")
	@NotNull(message = "Model is required")
	private String model;

	@NotNull(message = "Make is required")
	private VehicleMakeDto make;

	private LocalDateTime updatedAt;

	@JsonProperty(value = "updated_by")
	private Long updatedBy;

	@JsonProperty(value = "created_at")
	private LocalDateTime createdAt;

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public VehicleMakeDto getMake() {
		return make;
	}

	public void setMake(VehicleMakeDto make) {
		this.make = make;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}