package app.g_agent.reference_data_service.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.AdministrativeArea;

public interface AdministrativeAreaRepository extends JpaRepository<AdministrativeArea, Long> {
    Optional<AdministrativeArea> findById(Long id);
    List<AdministrativeArea> findByLevel(Integer level);
    List<AdministrativeArea> findByParentId(Long parentId);
    List<AdministrativeArea> findByCountryCode(String countryCode);
}