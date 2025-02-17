package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.DocumentTypeDto;
import app.g_agent.reference_data_service.model.DocumentType;
import app.g_agent.reference_data_service.repository.DocumentTypeRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class DocumentTypeService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentTypeService.class);

    private final DocumentTypeRepository documentTypeRepository;
    private JwtService jwtService;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
        this.jwtService = jwtService;
    }

    public DocumentType getDocumentTypeById(Long id) throws Exception {
        Optional<DocumentType> documentType = documentTypeRepository.getDocumentTypeById(id);
        if (documentType.isPresent()) {
            return documentType.get();
        } else {
            throw new Exception("The Document Type does not exist");
        }
    }

    @Transactional
    public void createDocumentType(HttpServletRequest request, DocumentTypeDto documentTypeDto) throws Exception {
    	Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());

        DocumentType documentType = new DocumentType();
        documentType.setName(documentTypeDto.getName());
        documentType.setUpdatedBy(userId);

        try {
            documentTypeRepository.save(documentType);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Document Type error ==========> id: " + ex.getMessage());
                throw new Exception("This document type already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void updateDocumentType(HttpServletRequest request, Long id, DocumentTypeDto documentTypeDto) throws Exception {
    	Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());
        Optional<DocumentType> documentTypeOpt = documentTypeRepository.getDocumentTypeById(id);

        if (documentTypeOpt.isEmpty()) {
            throw new Exception("The document type cannot be found");
        }

        DocumentType documentType = documentTypeOpt.get();
        documentType.setName(documentTypeDto.getName());
        documentType.setUpdatedBy(userId);

        try {
            documentTypeRepository.save(documentType);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Document Type error ==========> id: " + ex.getMessage());
                throw new Exception("This document type already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void deleteDocumentType(HttpServletRequest request, Long id) throws Exception {
        Optional<DocumentType> documentTypeOpt = documentTypeRepository.getDocumentTypeById(id);

        if (documentTypeOpt.isPresent()) {
            documentTypeRepository.delete(documentTypeOpt.get());
        } else {
            throw new Exception("The document type cannot be found");
        }
    }

    public DocumentTypeDto getDocumentType(HttpServletRequest request, Long id) throws Exception {
        Optional<DocumentType> documentTypeOpt = documentTypeRepository.getDocumentTypeById(id);
        if (documentTypeOpt.isPresent()) {
            DocumentType documentType = documentTypeOpt.get();
            DocumentTypeDto documentTypeDto = new DocumentTypeDto();
            documentTypeDto.setId(documentType.getId());
            documentTypeDto.setName(documentType.getName());
            documentTypeDto.setCreatedAt(documentType.getCreatedAt());
            documentTypeDto.setUpdatedAt(documentType.getUpdatedAt());
            documentTypeDto.setUpdatedBy(documentType.getUpdatedBy());
            return documentTypeDto;
        } else {
            throw new Exception("The Document Type does not exist");
        }
    }

    public List<DocumentTypeDto> getDocumentTypes(HttpServletRequest request) throws Exception {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        return documentTypes.stream().map(documentType -> {
            DocumentTypeDto documentTypeDto = new DocumentTypeDto();
            documentTypeDto.setId(documentType.getId());
            documentTypeDto.setName(documentType.getName());
            documentTypeDto.setCreatedAt(documentType.getCreatedAt());
            documentTypeDto.setUpdatedAt(documentType.getUpdatedAt());
            documentTypeDto.setUpdatedBy(documentType.getUpdatedBy());
            return documentTypeDto;
        }).collect(Collectors.toList());
    }
}