package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.reference_data_service.model.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> getPolicyById(Long id);
    Optional<Policy> getPolicyByName(String name);
}