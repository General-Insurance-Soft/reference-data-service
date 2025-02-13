package app.g_agent.reference_data_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.reference_data_service.commons.Message;
import app.g_agent.reference_data_service.dto.VehicleModelDto;
import app.g_agent.reference_data_service.service.VehicleModelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicle-model")
@Validated
public class VehicleModelController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleModelController.class);

    private final VehicleModelService vehicleModelService;

    public VehicleModelController(VehicleModelService vehicleModelService) {
        this.vehicleModelService = vehicleModelService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVehicleModel(HttpServletRequest request, @Valid @RequestBody VehicleModelDto vehicleModelDto) {
        try {
            vehicleModelService.createVehicleModel(vehicleModelDto);
            return ResponseEntity.ok(new Message("Vehicle model created successfully"));
        } catch (Exception e) {
            logger.error("Error creating vehicle model", e);
            return ResponseEntity.badRequest().body(new Message(e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicleModel(HttpServletRequest request, @RequestBody VehicleModelDto vehicleModelDto, @RequestParam Long id) {
        try {
            vehicleModelService.updateVehicleModel(id, vehicleModelDto);
            return ResponseEntity.ok(new Message("Vehicle model updated successfully"));
        } catch (Exception e) {
            logger.error("Error updating vehicle model", e);
            return ResponseEntity.badRequest().body(new Message(e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicleModel(HttpServletRequest request, @RequestParam Long id) {
        try {
            vehicleModelService.deleteVehicleModel(id);
            return ResponseEntity.ok(new Message("Vehicle model deleted successfully"));
        } catch (Exception e) {
            logger.error("Error deleting vehicle model", e);
            return ResponseEntity.badRequest().body(new Message(e.getMessage()));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getVehicleModel(HttpServletRequest request, @RequestParam Long id) {
        try {
            return ResponseEntity.ok(vehicleModelService.getVehicleModelById(id));
        } catch (Exception e) {
            logger.error("Error retrieving vehicle model", e);
            return ResponseEntity.badRequest().body(new Message(e.getMessage()));
        }
    }

    @GetMapping("/get-vehicle-models")
    public ResponseEntity<?> getVehicleModels(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(vehicleModelService.getAllVehicleModels());
        } catch (Exception e) {
            logger.error("Error retrieving vehicle models", e);
            return ResponseEntity.badRequest().body(new Message(e.getMessage()));
        }
    }
}