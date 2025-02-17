package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    Optional<DocumentType> getDocumentTypeById(Long id);
    Optional<DocumentType> getDocumentTypeByName(String name);
}