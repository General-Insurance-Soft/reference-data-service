package app.g_agent.reference_data_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.g_agent.reference_data_service.commons.Message;
import app.g_agent.reference_data_service.dto.PaymentMethodModeDto;
import app.g_agent.reference_data_service.service.PaymentMethodModeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment-method-mode")
@Validated
public class PaymentMethodModeController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodModeController.class);

	@Autowired
	PaymentMethodModeService paymentMethodModeService;

	@PostMapping("/create")
	private ResponseEntity<?> createPaymentMethodMode(HttpServletRequest request, @Valid @RequestBody PaymentMethodModeDto paymentMethodMode) {
		Message message = new Message();

		try {
			paymentMethodModeService.createPaymentMethodMode(request, paymentMethodMode);
		} catch (Exception ex) {
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
		message.setName("Success");
		message.setMessage("Payment method created successfully");
		return ResponseEntity.ok(message);
	}

	@PutMapping("/update")
	private ResponseEntity<?> updatePaymentMethodMode(HttpServletRequest request, @RequestBody PaymentMethodModeDto paymentMethodMode,
			@RequestParam Long id) {
		Message message = new Message();

		try {
			paymentMethodModeService.updatePaymentMethodMode(request, id, paymentMethodMode);
		} catch (Exception ex) {
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
		message.setName("Success");
		message.setMessage("Payment method updated successfully");
		return ResponseEntity.ok(message);
	}

	@DeleteMapping("/delete")
	private ResponseEntity<?> deletePaymentMethodMode(HttpServletRequest request, @RequestParam Long id) {
		Message message = new Message();

		try {
			paymentMethodModeService.deletePaymentMethodMode(request, id);
		} catch (Exception ex) {
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
		message.setName("Success");
		message.setMessage("Payment method deleted successfully");
		return ResponseEntity.ok(message);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getPaymentMethodMode(HttpServletRequest request, @RequestParam Long id) {
		Message message = new Message();

		try {
			return ResponseEntity.ok(paymentMethodModeService.getPaymentMethodMode(request, id));
		} catch (Exception ex) {
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}

	@GetMapping("/get-payment-method-modes")
	public ResponseEntity<?> getPaymentMethodModes(HttpServletRequest request) {
		Message message = new Message();

		try {
			return ResponseEntity.ok(paymentMethodModeService.getPaymentMethodModes(request));
		} catch (Exception ex) {
			message.setName("Error");
			message.setMessage(ex.getMessage());
			return ResponseEntity.status(403).body(message);
		}
	}
}