package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.PoliceStationDto;
import app.g_agent.reference_data_service.model.PoliceStation;
import app.g_agent.reference_data_service.repository.PoliceStationRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PoliceStationService {

	private static final Logger logger = LoggerFactory.getLogger(PoliceStationService.class);

	private final PoliceStationRepository policeStationRepository;
	private JwtService jwtService;

	public PoliceStationService(PoliceStationRepository policeStationRepository, JwtService jwtService) {
		this.policeStationRepository = policeStationRepository;
		this.jwtService = jwtService;
	}

	public PoliceStation getPoliceStationById(Long id) throws Exception {
		Optional<PoliceStation> policeStation = policeStationRepository.getPoliceStationById(id);
		if (policeStation.isPresent()) {
			return policeStation.get();
		} else {
			throw new Exception("The Police Station does not exist");
		}
	}

	public void createPoliceStation(HttpServletRequest request, PoliceStationDto policeStationDto) throws Exception {
		Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());
		PoliceStation policeStation = new PoliceStation();

		policeStation.setName(policeStationDto.getName());
		policeStation.setJurisdiction(policeStationDto.getJurisdiction());
		policeStation.setCode(policeStationDto.getCode());
		policeStation.setEmail(policeStationDto.getEmail());
		policeStation.setAddress(policeStationDto.getAddress());
		policeStation.setPhone(policeStationDto.getPhone());
		policeStation.setAddress(policeStationDto.getAddress());
		policeStation.setUpdatedBy(userId);

		try {
			policeStationRepository.save(policeStation);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Police Station error ==========> id: " + ex.getMessage());
				throw new Exception("This police station already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void updatePoliceStation(HttpServletRequest request, Long id, PoliceStationDto policeStationDto)
			throws Exception {
		Optional<PoliceStation> policeStationOpt = policeStationRepository.getPoliceStationById(id);
		Long userId = (Long) jwtService.getTokenValue(jwtService.getJWT(request), "user-id");

		if (policeStationOpt.isEmpty()) {
			throw new Exception("The police station cannot be found");
		}

		PoliceStation policeStation = policeStationOpt.get();
		policeStation.setName(policeStationDto.getName());
		policeStation.setJurisdiction(policeStationDto.getJurisdiction());
		policeStation.setCode(policeStationDto.getCode());
		policeStation.setEmail(policeStationDto.getEmail());
		policeStation.setAddress(policeStationDto.getAddress());
		policeStation.setPhone(policeStationDto.getPhone());
		policeStation.setUpdatedBy(userId);
		policeStation.setUpdatedBy(userId);

		try {
			policeStationRepository.save(policeStation);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Police Station error ==========> id: " + ex.getMessage());
				throw new Exception("This police station already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void deletePoliceStation(HttpServletRequest request, Long id) throws Exception {
		Optional<PoliceStation> policeStationOpt = policeStationRepository.getPoliceStationById(id);

		if (policeStationOpt.isPresent()) {
			policeStationRepository.delete(policeStationOpt.get());
		} else {
			throw new Exception("The police station cannot be found");
		}
	}

	public PoliceStationDto getPoliceStation(HttpServletRequest request, Long id) throws Exception {
		Optional<PoliceStation> policeStationOpt = policeStationRepository.getPoliceStationById(id);
		if (policeStationOpt.isPresent()) {
			PoliceStation policeStation = policeStationOpt.get();
			PoliceStationDto policeStationDto = new PoliceStationDto();
			policeStationDto.setId(policeStation.getId());
			policeStationDto.setName(policeStation.getName());
			policeStationDto.setJurisdiction(policeStation.getJurisdiction());
			policeStationDto.setCode(policeStation.getCode());
			policeStationDto.setEmail(policeStation.getEmail());
			policeStationDto.setPhone(policeStation.getPhone());
			policeStationDto.setAddress(policeStation.getAddress());
			policeStationDto.setCreatedAt(policeStation.getCreatedAt());
			return policeStationDto;
		} else {
			throw new Exception("The Police Station does not exist");
		}
	}

	public List<PoliceStationDto> getPoliceStations(HttpServletRequest request) throws Exception {
		List<PoliceStation> policeStations = policeStationRepository.findAll();
		return policeStations.stream().map(policeStation -> {
			PoliceStationDto policeStationDto = new PoliceStationDto();
			policeStationDto.setId(policeStation.getId());
			policeStationDto.setName(policeStation.getName());
			policeStationDto.setJurisdiction(policeStation.getJurisdiction());
			policeStationDto.setCode(policeStation.getCode());
			policeStationDto.setEmail(policeStation.getEmail());
			policeStationDto.setPhone(policeStation.getPhone());
			policeStationDto.setAddress(policeStation.getAddress());
			policeStationDto.setCreatedAt(policeStation.getCreatedAt());
			return policeStationDto;
		}).collect(Collectors.toList());
	}
}