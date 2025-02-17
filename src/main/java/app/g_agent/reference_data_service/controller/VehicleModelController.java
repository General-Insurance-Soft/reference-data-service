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
        
        Message message = new Message();
        
        try {
        	vehicleModelService.createVehicleModel( request,vehicleModelDto);
            message.setName("Success");
            message.setMessage("Vehicle model created successfully");
            return ResponseEntity.ok(message);
        } catch (Exception ex) {
            logger.error("Error creating vehicle model", ex);
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicleModel(HttpServletRequest request, @RequestBody VehicleModelDto vehicleModelDto, @RequestParam Long id) {
       
        
        Message message = new Message();

        try {
        	vehicleModelService.updateVehicleModel(request, id, vehicleModelDto);
            message.setName("Success");
            message.setMessage("Vehicle model updated successfully");
            return ResponseEntity.ok(message);
        } catch (Exception ex) {
            logger.error("Error updating vehicle model", ex);
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicleModel(HttpServletRequest request, @RequestParam Long id) {
       
        
        Message message = new Message();

        try {
        	vehicleModelService.deleteVehicleModel(id);
            message.setName("Success");
            message.setMessage("Vehicle model deleted successfully");
            return ResponseEntity.ok(message);
        } catch (Exception ex) {
            logger.error("Error deleting vehicle model", ex);
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getVehicleModel(HttpServletRequest request, @RequestParam Long id) {
        
        Message message = new Message();

        try {
            return ResponseEntity.ok(vehicleModelService.getVehicleModelById(id));
        } catch (Exception ex) {
            logger.error("Error retrieving vehicle model", ex);
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get-vehicle-models")
    public ResponseEntity<?> getVehicleModels(HttpServletRequest request) {
       
        Message message = new Message();

        try {
			return ResponseEntity.ok(vehicleModelService.getAllVehicleModels());
        } catch (Exception ex) {
            logger.error("Error retrieving vehicle models", ex);
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        
        
    }
}