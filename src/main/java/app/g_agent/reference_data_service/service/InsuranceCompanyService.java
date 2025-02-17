package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.InsuranceCompanyDto;
import app.g_agent.reference_data_service.model.InsuranceCompany;
import app.g_agent.reference_data_service.repository.InsuranceCompanyRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class InsuranceCompanyService {

	private static final Logger logger = LoggerFactory.getLogger(InsuranceCompanyService.class);

	private final InsuranceCompanyRepository insuranceCompanyRepository;
	private JwtService jwtService;

	public InsuranceCompanyService(InsuranceCompanyRepository insuranceCompanyRepository, JwtService jwtService) {
		this.insuranceCompanyRepository = insuranceCompanyRepository;
		this.jwtService = jwtService;
	}

	public InsuranceCompany getInsuranceCompanyById(Long id) throws Exception {
		Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.getInsuranceCompanyById(id);
		if (insuranceCompany.isPresent()) {
			return insuranceCompany.get();
		} else {
			throw new Exception("The Insurance Company does not exist");
		}
	}

	@Transactional
	public void createInsuranceCompany(HttpServletRequest request, InsuranceCompanyDto insuranceCompanyDto)
			throws Exception {
		InsuranceCompany insuranceCompany = new InsuranceCompany();
		Long userId = (Long) jwtService.getTokenValue(jwtService.getJWT(request), "user-id");

		insuranceCompany.setName(insuranceCompanyDto.getName());
		insuranceCompany.setCode(insuranceCompanyDto.getCode());
		insuranceCompany.setContactEmail(insuranceCompanyDto.getContactEmail());
		insuranceCompany.setContactPhone(insuranceCompanyDto.getContactPhone());
		insuranceCompany.setAddress(insuranceCompanyDto.getAddress());
		insuranceCompany.setUpdatedBy(userId);

		try {
			insuranceCompanyRepository.save(insuranceCompany);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Insurance Company error ==========> id: " + ex.getMessage());
				throw new Exception("This insurance company already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void updateInsuranceCompany(HttpServletRequest request, Long id, InsuranceCompanyDto insuranceCompanyDto)
			throws Exception {
		Optional<InsuranceCompany> insuranceCompanyOpt = insuranceCompanyRepository.getInsuranceCompanyById(id);
		Long userId = (Long) jwtService.getTokenValue(jwtService.getJWT(request), "user-id");

		if (insuranceCompanyOpt.isEmpty()) {
			throw new Exception("The insurance company cannot be found");
		}

		InsuranceCompany insuranceCompany = insuranceCompanyOpt.get();
		insuranceCompany.setName(insuranceCompanyDto.getName());
		insuranceCompany.setCode(insuranceCompanyDto.getCode());
		insuranceCompany.setContactEmail(insuranceCompanyDto.getContactEmail());
		insuranceCompany.setContactPhone(insuranceCompanyDto.getContactPhone());
		insuranceCompany.setAddress(insuranceCompanyDto.getAddress());
		insuranceCompany.setUpdatedBy(userId);

		try {
			insuranceCompanyRepository.save(insuranceCompany);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Insurance Company error ==========> id: " + ex.getMessage());
				throw new Exception("This insurance company already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void deleteInsuranceCompany(HttpServletRequest request, Long id) throws Exception {
		Optional<InsuranceCompany> insuranceCompanyOpt = insuranceCompanyRepository.getInsuranceCompanyById(id);

		if (insuranceCompanyOpt.isPresent()) {
			insuranceCompanyRepository.delete(insuranceCompanyOpt.get());
		} else {
			throw new Exception("The insurance company cannot be found");
		}
	}

	public InsuranceCompanyDto getInsuranceCompany(HttpServletRequest request, Long id) throws Exception {
		Optional<InsuranceCompany> insuranceCompanyOpt = insuranceCompanyRepository.getInsuranceCompanyById(id);
		if (insuranceCompanyOpt.isPresent()) {
			InsuranceCompany insuranceCompany = insuranceCompanyOpt.get();
			InsuranceCompanyDto insuranceCompanyDto = new InsuranceCompanyDto();
			insuranceCompanyDto.setId(insuranceCompany.getId());
			insuranceCompanyDto.setName(insuranceCompany.getName());
			insuranceCompanyDto.setCode(insuranceCompany.getCode());
			insuranceCompanyDto.setContactEmail(insuranceCompany.getContactEmail());
			insuranceCompanyDto.setContactPhone(insuranceCompany.getContactPhone());
			insuranceCompanyDto.setAddress(insuranceCompany.getAddress());
			insuranceCompanyDto.setCreatedAt(insuranceCompany.getCreatedAt());
			return insuranceCompanyDto;
		} else {
			throw new Exception("The Insurance Company does not exist");
		}
	}

	public List<InsuranceCompanyDto> getInsuranceCompanies(HttpServletRequest request) throws Exception {
		List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findAll();
		return insuranceCompanies.stream().map(insuranceCompany -> {
			InsuranceCompanyDto insuranceCompanyDto = new InsuranceCompanyDto();
			insuranceCompanyDto.setId(insuranceCompany.getId());
			insuranceCompanyDto.setName(insuranceCompany.getName());
			insuranceCompanyDto.setCode(insuranceCompany.getCode());
			insuranceCompanyDto.setContactEmail(insuranceCompany.getContactEmail());
			insuranceCompanyDto.setContactPhone(insuranceCompany.getContactPhone());
			insuranceCompanyDto.setAddress(insuranceCompany.getAddress());
			insuranceCompanyDto.setCreatedAt(insuranceCompany.getCreatedAt());
			return insuranceCompanyDto;
		}).collect(Collectors.toList());
	}
}