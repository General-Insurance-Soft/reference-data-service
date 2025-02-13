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
import app.g_agent.reference_data_service.dto.InsuranceCompanyDto;
import app.g_agent.reference_data_service.service.InsuranceCompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/insurance-company")
@Validated
public class InsuranceCompanyController {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceCompanyController.class);

    @Autowired
    InsuranceCompanyService insuranceCompanyService;

    @PostMapping("/create")
    public ResponseEntity<?> createInsuranceCompany(HttpServletRequest request, @Valid @RequestBody InsuranceCompanyDto insuranceCompanyDto) {
        Message message = new Message();

        try {
            insuranceCompanyService.createInsuranceCompany(request, insuranceCompanyDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Insurance Company created successfully");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInsuranceCompany(HttpServletRequest request, @RequestBody InsuranceCompanyDto insuranceCompanyDto,
                                                    @RequestParam Long id) {
        Message message = new Message();

        try {
            insuranceCompanyService.updateInsuranceCompany(request, id, insuranceCompanyDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Insurance Company updated successfully");
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInsuranceCompany(HttpServletRequest request, @RequestParam Long id) {
        Message message = new Message();

        try {
            insuranceCompanyService.deleteInsuranceCompany(request, id);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Insurance Company deleted successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getInsuranceCompany(HttpServletRequest request, @RequestParam Long id) {
        Message message = new Message();

        try {
            return ResponseEntity.ok(insuranceCompanyService.getInsuranceCompany(request, id));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get-insurance-companies")
    public ResponseEntity<?> getInsuranceCompanies(HttpServletRequest request) {
        Message message = new Message();

        try {
            return ResponseEntity.ok(insuranceCompanyService.getInsuranceCompanies(request));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }
}