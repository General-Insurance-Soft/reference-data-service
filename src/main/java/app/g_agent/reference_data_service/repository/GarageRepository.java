package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.Garage;

public interface GarageRepository extends JpaRepository<Garage, Long> {
	Optional<Garage> getGarageById(Long id);

	Optional<Garage> getGarageByIdAndOrganizationId(Long id, Long organizationId);

	Optional<Garage> getGarageByName(String name);

	Optional<Garage> getGarageByNameAndOrganizationId(String name, Long organizationId);
}