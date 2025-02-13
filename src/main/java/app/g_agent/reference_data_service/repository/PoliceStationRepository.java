package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.PoliceStation;

public interface PoliceStationRepository extends JpaRepository<PoliceStation, Long> {
    Optional<PoliceStation> getPoliceStationById(Long id);
    Optional<PoliceStation> getPoliceStationByName(String name);
}