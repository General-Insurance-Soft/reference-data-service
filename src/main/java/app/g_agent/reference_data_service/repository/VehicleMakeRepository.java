package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.VehicleMake;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {
    Optional<VehicleMake> getVehicleMakeById(Long id);
    Optional<VehicleMake> getVehicleMakeByMake(String make);
}