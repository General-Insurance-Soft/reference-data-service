package app.g_agent.reference_data_service.repository;

import java.util.Optional;

import app.g_agent.reference_data_service.model.Garage;
import app.g_agent.reference_data_service.model.PaymentMethodMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodModeRepository extends JpaRepository<PaymentMethodMode, Long> {

    Optional<PaymentMethodMode> findById(Long id);

    Optional<PaymentMethodMode> findByName(String name);

    Optional<PaymentMethodMode> findByCode(String code);
}
