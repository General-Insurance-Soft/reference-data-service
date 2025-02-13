package app.g_agent.reference_data_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.VehicleModel;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
    Optional<VehicleModel> getVehicleModelById(Long id);
    Optional<List<VehicleModel>> getVehicleModelsByMakeId(Long makeId);
}