package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> getHospitalById(Long id);
    Optional<Hospital> getHospitalByName(String name);
}