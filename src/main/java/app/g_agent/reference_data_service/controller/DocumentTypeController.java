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
import app.g_agent.reference_data_service.dto.DocumentTypeDto;
import app.g_agent.reference_data_service.service.DocumentTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/document-type")
@Validated
public class DocumentTypeController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentTypeController.class);

    @Autowired
    DocumentTypeService documentTypeService;

    @PostMapping("/create")
    public ResponseEntity<?> createDocumentType(HttpServletRequest request,
            @Valid @RequestBody DocumentTypeDto documentTypeDto) {
        Message message = new Message();

        try {
            documentTypeService.createDocumentType(request, documentTypeDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Document Type created successfully");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDocumentType(HttpServletRequest request,
            @RequestBody DocumentTypeDto documentTypeDto,
            @RequestParam Long id) {
        Message message = new Message();

        try {
            documentTypeService.updateDocumentType(request, id, documentTypeDto);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Document Type updated successfully");
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDocumentType(HttpServletRequest request, @RequestParam Long id) {
        Message message = new Message();

        try {
            documentTypeService.deleteDocumentType(request, id);
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
        message.setName("Success");
        message.setMessage("Document Type deleted successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getDocumentType(HttpServletRequest request, @RequestParam Long id) {
        logger.info("Fetching document type with ID: {}", id);
        Message message = new Message();

        try {
            return ResponseEntity.ok(documentTypeService.getDocumentType(request, id));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }

    @GetMapping("/get-document-types")
    public ResponseEntity<?> getDocumentTypes(HttpServletRequest request) {
        logger.info("Fetching all document types");
        Message message = new Message();

        try {
            return ResponseEntity.ok(documentTypeService.getDocumentTypes(request));
        } catch (Exception ex) {
            message.setName("Error");
            message.setMessage(ex.getMessage());
            return ResponseEntity.status(403).body(message);
        }
    }
}