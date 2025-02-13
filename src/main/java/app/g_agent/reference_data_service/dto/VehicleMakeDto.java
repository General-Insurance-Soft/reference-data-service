package app.g_agent.reference_data_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleMakeDto {

    private Long id;

    @NotBlank(message = "Make is required")
    @NotNull(message = "Make is required")
    private String make;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}