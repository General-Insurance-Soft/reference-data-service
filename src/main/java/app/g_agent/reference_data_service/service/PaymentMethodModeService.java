package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.PaymentMethodModeDto;
import app.g_agent.reference_data_service.model.PaymentMethodMode;
import app.g_agent.reference_data_service.repository.PaymentMethodModeRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaymentMethodModeService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodModeService.class);

	private PaymentMethodModeRepository paymentMethodModeRepository;
	private JwtService jwtService;

	public PaymentMethodModeService(PaymentMethodModeRepository paymentMethodModeRepository, JwtService jwtService) {
		this.paymentMethodModeRepository = paymentMethodModeRepository;
		this.jwtService = jwtService;
	}

	public void createPaymentMethodMode(HttpServletRequest request, PaymentMethodModeDto paymentMethodModeDto)
			throws Exception {
		logger.info("Create PaymentMethodMode service request ==========> userId: "
				+ jwtService.getTokenValue(jwtService.getJWT(request), "user-id"));

		PaymentMethodMode paymentMethodMode = new PaymentMethodMode();
		logger.info("Create PaymentMethodMode model to save ==========> ");
		paymentMethodMode.setName(paymentMethodModeDto.getName());
		paymentMethodMode.setCode(paymentMethodModeDto.getCode());

		logger.info("Attemp to persist PaymentMethodMode model  ==========> ");
		try {
			paymentMethodModeRepository.save(paymentMethodMode);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("PaymentMethodMode error ==========> id: " + ex.getMessage());
				throw new Exception("This PaymentMethodMode already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void updatePaymentMethodMode(HttpServletRequest request, Long id, PaymentMethodModeDto paymentMethodModeDto)
			throws Exception {

		Optional<PaymentMethodMode> paymentMethodModeOpt = paymentMethodModeRepository
				.findById(paymentMethodModeDto.getId());

		if (paymentMethodModeOpt.isEmpty()) {
			throw new Exception("The PaymentMethodMode cannot be found");
		}

		PaymentMethodMode paymentMethodMode = paymentMethodModeOpt.get();
		paymentMethodMode.setName(paymentMethodModeDto.getName());
		paymentMethodMode.setCode(paymentMethodModeDto.getCode());

		try {
			paymentMethodModeRepository.save(paymentMethodMode);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("PaymentMethodMode error ==========> id: " + ex.getMessage());
				throw new Exception("This PaymentMethodMode already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void deletePaymentMethodMode(HttpServletRequest request, Long id) throws Exception {

		Optional<PaymentMethodMode> paymentMethodModeOpt = paymentMethodModeRepository.findById(id);

		if (paymentMethodModeOpt.isPresent()) {
			paymentMethodModeRepository.delete(paymentMethodModeOpt.get());
		} else {
			throw new Exception("The PaymentMethodMode cannot be found");
		}
	}

	public PaymentMethodModeDto getPaymentMethodMode(HttpServletRequest request, Long id) throws Exception {
		Optional<PaymentMethodMode> paymentMethodModeOpt = paymentMethodModeRepository.findById(id);
		if (paymentMethodModeOpt.isPresent()) {
			PaymentMethodMode paymentMethodMode = paymentMethodModeOpt.get();
			PaymentMethodModeDto paymentMethodModeDto = new PaymentMethodModeDto();
			paymentMethodModeDto.setId(paymentMethodMode.getId());
			paymentMethodModeDto.setName(paymentMethodMode.getName());
			paymentMethodModeDto.setCode(paymentMethodMode.getCode());
			return paymentMethodModeDto;
		} else {
			throw new Exception("The PaymentMethodMode does not exist");
		}
	}

	public List<PaymentMethodModeDto> getPaymentMethodModes(HttpServletRequest request) throws Exception {
		List<PaymentMethodMode> paymentMethodModes = paymentMethodModeRepository.findAll(); // findall
		return paymentMethodModes.stream().map(paymentMethodMode -> {
			PaymentMethodModeDto paymentMethodModeDto = new PaymentMethodModeDto();
			paymentMethodModeDto.setId(paymentMethodMode.getId());
			paymentMethodModeDto.setName(paymentMethodMode.getName());
			paymentMethodModeDto.setCode(paymentMethodMode.getCode());
			return paymentMethodModeDto;
		}).collect(Collectors.toList());
	}
}