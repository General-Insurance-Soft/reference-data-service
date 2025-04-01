package app.g_agent.reference_data_service.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.g_agent.reference_data_service.commons.Message;
import app.g_agent.reference_data_service.dto.AdministrativeAreaDto;
import app.g_agent.reference_data_service.service.AdministrativeAreaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/administrative-area")
@Validated
public class AdministrativeAreaController {

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeAreaController.class);

    @Autowired
    AdministrativeAreaService administrativeAreaService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdministrativeArea(HttpServletRequest request,
            @Valid @RequestBody AdministrativeAreaDto areaDto) {
        Message message = new Message();

        try {
            administrativeAreaService.createAdministrativeArea(request, areaDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Administrative Area created successfully");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAdministrativeArea(HttpServletRequest request,
            @RequestBody AdministrativeAreaDto areaDto,
            @RequestParam Long id) {
        Message message = new Message();

        try {
            administrativeAreaService.updateAdministrativeArea(request, id, areaDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Administrative Area updated successfully");
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAdministrativeArea(HttpServletRequest request, @RequestParam Long id) {
        Message message = new Message();

        try {
            administrativeAreaService.deleteAdministrativeArea(request, id);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Administrative Area deleted successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAdministrativeArea(HttpServletRequest request, @RequestParam Long id) {
        logger.info("Fetching administrative area with ID: {}", id);
        Message message = new Message();

        try {
            return ResponseEntity.ok(administrativeAreaService.getAdministrativeArea(request, id));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get-administrative-areas")
    public ResponseEntity<?> getAdministrativeAreas(HttpServletRequest request) {
        logger.info("Fetching all administrative areas");
        Message message = new Message();

        try {
            return ResponseEntity.ok(administrativeAreaService.getAdministrativeAreas(request));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get-administrative-areas-by-ids")
    public ResponseEntity<?> getAdministrativeAreasByIds(HttpServletRequest request,
            @RequestParam String locationIds) {
        logger.info("Fetching all administrative areas of size {} ========> " + locationIds);
        List<Long> ids = Arrays.stream(locationIds.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();

        try {
            return ResponseEntity.ok(administrativeAreaService.getAdministrativeAreas(request, ids));
            // return ResponseEntity.ok("DOne");
        } catch (Exception ex) {
            Message message = new Message();
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }
}