package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.GarageDto;
import app.g_agent.reference_data_service.model.Garage;
import app.g_agent.reference_data_service.repository.GarageRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class GarageService {

	private static final Logger logger = LoggerFactory.getLogger(GarageService.class);

	private GarageRepository garageRepository;
	private JwtService jwtService;

	public GarageService(GarageRepository garageRepository, JwtService jwtService) {
		this.garageRepository = garageRepository;
		this.jwtService = jwtService;
	}

	public Garage getGarageByIdAndOrganizationId(HttpServletRequest request, Long id) throws Exception {
		Long orgId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "organization-id").toString());
		Optional<Garage> garage = garageRepository.getGarageByIdAndOrganizationId(id, orgId);
		if (garage.isPresent()) {
			return garage.get();
		} else {
			throw new Exception("The Garage does not exist");
		}
	}

	public void createGarage(HttpServletRequest request, GarageDto garageDto) throws Exception {
		logger.info("Create Garage service request ==========> userId: "
				+ jwtService.getTokenValue(jwtService.getJWT(request), "user-id"));

		Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());
		Long orgId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "organization-id").toString());

		Garage garage = new Garage();
		logger.info("Create Garage model to save ==========> ");
		garage.setName(garageDto.getName());
		garage.setLocation(garageDto.getLocation());
		garage.setApprovedInsuranceCompanies(garageDto.getApprovedInsuranceCompanies());
		garage.setContactPhone(garageDto.getContactPhone());
		garage.setUpdatedBy(userId);
		garage.setOrganizationId(orgId);

		logger.info("Attemp to persist Garage model  ==========> ");
		try {
			garageRepository.save(garage);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Garage error ==========> id: " + ex.getMessage());
				throw new Exception("This garage already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void updateGarage(HttpServletRequest request, Long id, GarageDto garageDto) throws Exception {
		Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());
		Long orgId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "organization-id").toString());
		Optional<Garage> garageOpt = garageRepository.getGarageByIdAndOrganizationId(id, orgId);

		if (garageOpt.isEmpty()) {
			throw new Exception("The garage cannot be found");
		}

		Garage garage = garageOpt.get();
		garage.setName(garageDto.getName());
		garage.setLocation(garageDto.getLocation());
		garage.setApprovedInsuranceCompanies(garageDto.getApprovedInsuranceCompanies());
		garage.setContactPhone(garageDto.getContactPhone());
		garage.setUpdatedBy(userId);

		try {
			garageRepository.save(garage);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Garage error ==========> id: " + ex.getMessage());
				throw new Exception("This garage already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void deleteGarage(HttpServletRequest request, Long id) throws Exception {
		Long orgId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "organization-id").toString());
		Optional<Garage> garageOpt = garageRepository.getGarageByIdAndOrganizationId(id, orgId);

		if (garageOpt.isPresent()) {
			garageRepository.delete(garageOpt.get());
		} else {
			throw new Exception("The garage cannot be found");
		}
	}

	public GarageDto getGarage(HttpServletRequest request, Long id) throws Exception {
		Long orgId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "organization-id").toString());
		Optional<Garage> garageOpt = garageRepository.getGarageByIdAndOrganizationId(id, orgId);
		if (garageOpt.isPresent()) {
			Garage garage = garageOpt.get();
			GarageDto garageDto = new GarageDto();
			garageDto.setId(garage.getId());
			garageDto.setName(garage.getName());
			garageDto.setLocation(garage.getLocation());
			garageDto.setApprovedInsuranceCompanies(garage.getApprovedInsuranceCompanies());
			garageDto.setContactPhone(garage.getContactPhone());
			garageDto.setCreatedAt(garage.getCreatedAt());
			garageDto.setUpdatedAt(garage.getUpdatedAt());
			garageDto.setUpdatedBy(garage.getUpdatedBy());
			garageDto.setOrganizationId(garage.getOrganizationId());
			return garageDto;
		} else {
			throw new Exception("The Garage does not exist");
		}
	}

	public List<GarageDto> getGarages(HttpServletRequest request) throws Exception {
		List<Garage> garages = garageRepository.findAll();
		return garages.stream().map(garage -> {
			GarageDto garageDto = new GarageDto();
			garageDto.setId(garage.getId());
			garageDto.setName(garage.getName());
			garageDto.setLocation(garage.getLocation());
			garageDto.setApprovedInsuranceCompanies(garage.getApprovedInsuranceCompanies());
			garageDto.setContactPhone(garage.getContactPhone());
			garageDto.setCreatedAt(garage.getCreatedAt());
			garageDto.setUpdatedAt(garage.getUpdatedAt());
			garageDto.setUpdatedBy(garage.getUpdatedBy());
			garageDto.setOrganizationId(garage.getOrganizationId());
			return garageDto;
		}).collect(Collectors.toList());
	}
}