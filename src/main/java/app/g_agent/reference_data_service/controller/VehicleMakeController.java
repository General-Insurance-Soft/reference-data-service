package app.g_agent.reference_data_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import app.g_agent.reference_data_service.dto.VehicleMakeDto;
import app.g_agent.reference_data_service.service.VehicleMakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicle-make")
@Validated
public class VehicleMakeController {

	private static final Logger logger = LoggerFactory.getLogger(VehicleMakeController.class);

	@Autowired
	VehicleMakeService vehicleMakeService;

	@PostMapping("/create")
	public ResponseEntity<?> createVehicleMake(HttpServletRequest request,
			@Valid @RequestBody VehicleMakeDto vehicleMakeDto) {
		Message message = new Message();

		try {
			vehicleMakeService.createVehicleMake(request, vehicleMakeDto);
			message.setName("Success");
			message.setMessage("Vehicle Make created successfully");
			return ResponseEntity.ok(message);
		} catch (Exception ex) {
			logger.error("Error creating vehicle make", ex);
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateVehicleMake(HttpServletRequest request, @RequestBody VehicleMakeDto vehicleMakeDto,
			@RequestParam Long id) {
		Message message = new Message();

		try {
			vehicleMakeService.updateVehicleMake(request, id, vehicleMakeDto);
			message.setName("Success");
			message.setMessage("Vehicle Make updated successfully");
			return ResponseEntity.ok(message);
		} catch (Exception ex) {
			logger.error("Error updating vehicle make", ex);
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteVehicleMake(HttpServletRequest request, @RequestParam Long id) {
		Message message = new Message();

		try {
			vehicleMakeService.deleteVehicleMake(request, id);
			message.setName("Success");
			message.setMessage("Vehicle Make deleted successfully");
			return ResponseEntity.ok(message);
		} catch (Exception ex) {
			logger.error("Error deleting vehicle make", ex);
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getVehicleMake(HttpServletRequest request, @RequestParam Long id) {
		Message message = new Message();

		try {
			return ResponseEntity.ok(vehicleMakeService.getVehicleMake(request, id));
		} catch (Exception ex) {
			logger.error("Error retrieving vehicle make", ex);
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}

	@GetMapping("/get-vehicle-makes")
	public ResponseEntity<?> getVehicleMakes(HttpServletRequest request) {
		Message message = new Message();

		try {
			return ResponseEntity.ok(vehicleMakeService.getVehicleMakes(request));
		} catch (Exception ex) {
			logger.error("Error retrieving vehicle makes", ex);
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}
}