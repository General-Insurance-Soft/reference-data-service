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
import app.g_agent.reference_data_service.dto.PoliceStationDto;
import app.g_agent.reference_data_service.service.PoliceStationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/police-station")
@Validated
public class PoliceStationController {

    private static final Logger logger = LoggerFactory.getLogger(PoliceStationController.class);

    @Autowired
    PoliceStationService policeStationService;

    @PostMapping("/create")
    public ResponseEntity<?> createPoliceStation(HttpServletRequest request, @Valid @RequestBody PoliceStationDto policeStationDto) {
        Message message = new Message();

        try {
            policeStationService.createPoliceStation(request, policeStationDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Police Station created successfully");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePoliceStation(HttpServletRequest request, @RequestBody PoliceStationDto policeStationDto,
                                                 @RequestParam Long id) {
        Message message = new Message();

        try {
            policeStationService.updatePoliceStation(request, id, policeStationDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Police Station updated successfully");
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePoliceStation(HttpServletRequest request, @RequestParam Long id) {
        Message message = new Message();

        try {
            policeStationService.deletePoliceStation(request, id);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Police Station deleted successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPoliceStation(HttpServletRequest request, @RequestParam Long id) {
        Message message = new Message();

        try {
            return ResponseEntity.ok(policeStationService.getPoliceStation(request, id));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get-police-stations")
    public ResponseEntity<?> getPoliceStations(HttpServletRequest request) {
        Message message = new Message();

        try {
            return ResponseEntity.ok(policeStationService.getPoliceStations(request));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }
}