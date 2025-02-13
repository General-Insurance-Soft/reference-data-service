package app.g_agent.reference_data_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleModelDto {

    private Long id;

    @NotBlank(message = "Model is required")
    @NotNull(message = "Model is required")
    private String model;

    @NotNull(message = "Make is required")
    private VehicleMakeDto make;

    // Getters and Setters

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
}