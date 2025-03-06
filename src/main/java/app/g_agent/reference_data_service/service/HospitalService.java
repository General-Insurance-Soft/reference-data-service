package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.HospitalDto;
import app.g_agent.reference_data_service.model.Hospital;
import app.g_agent.reference_data_service.repository.HospitalRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class HospitalService {

	private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);

	private final HospitalRepository hospitalRepository;
	private JwtService jwtService;

	public HospitalService(HospitalRepository hospitalRepository, JwtService jwtService) {
		this.hospitalRepository = hospitalRepository;
		this.jwtService = jwtService;
	}

	public Hospital getHospitalById(Long id) throws Exception {
		Optional<Hospital> hospital = hospitalRepository.getHospitalById(id);
		if (hospital.isPresent()) {
			return hospital.get();
		} else {
			throw new Exception("The Hospital does not exist");
		}
	}

	public void createHospital(HttpServletRequest request, HospitalDto hospitalDto) throws Exception {
		Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());
		Hospital hospital = new Hospital();

		hospital.setName(hospitalDto.getName());
		hospital.setLocation(hospitalDto.getLocation());
		hospital.setApprovedInsuranceCompanies(hospitalDto.getApprovedInsuranceCompanies());
		hospital.setContactPhone(hospitalDto.getContactPhone());
		hospital.setUpdatedBy(userId);

		try {
			hospitalRepository.save(hospital);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Hospital error ==========> id: " + ex.getMessage());
				throw new Exception("This hospital already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void updateHospital(HttpServletRequest request, Long id, HospitalDto hospitalDto) throws Exception {
		Optional<Hospital> hospitalOpt = hospitalRepository.getHospitalById(id);
		Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());

		if (hospitalOpt.isEmpty()) {
			throw new Exception("The hospital cannot be found");
		}

		Hospital hospital = hospitalOpt.get();
		hospital.setName(hospitalDto.getName());
		hospital.setLocation(hospitalDto.getLocation());
		hospital.setApprovedInsuranceCompanies(hospitalDto.getApprovedInsuranceCompanies());
		hospital.setContactPhone(hospitalDto.getContactPhone());
		hospital.setUpdatedBy(userId);

		try {
			hospitalRepository.save(hospital);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				logger.info("Hospital error ==========> id: " + ex.getMessage());
				throw new Exception("This hospital already exists.");
			}
			throw ex; // Rethrow if not related to constraint violation
		}
	}

	@Transactional
	public void deleteHospital(HttpServletRequest request, Long id) throws Exception {
		Optional<Hospital> hospitalOpt = hospitalRepository.getHospitalById(id);

		if (hospitalOpt.isPresent()) {
			hospitalRepository.delete(hospitalOpt.get());
		} else {
			throw new Exception("The hospital cannot be found");
		}
	}

	public HospitalDto getHospital(HttpServletRequest request, Long id) throws Exception {
		Optional<Hospital> hospitalOpt = hospitalRepository.getHospitalById(id);
		if (hospitalOpt.isPresent()) {
			Hospital hospital = hospitalOpt.get();
			HospitalDto hospitalDto = new HospitalDto();
			hospitalDto.setId(hospital.getId());
			hospitalDto.setName(hospital.getName());
			hospitalDto.setLocation(hospital.getLocation());
			hospitalDto.setApprovedInsuranceCompanies(hospital.getApprovedInsuranceCompanies());
			hospitalDto.setContactPhone(hospital.getContactPhone());
			hospitalDto.setCreatedAt(hospital.getCreatedAt());
			return hospitalDto;
		} else {
			throw new Exception("The Hospital does not exist");
		}
	}

	public List<HospitalDto> getHospitals(HttpServletRequest request) throws Exception {
		List<Hospital> hospitals = hospitalRepository.findAll();
		return hospitals.stream().map(hospital -> {
			HospitalDto hospitalDto = new HospitalDto();
			hospitalDto.setId(hospital.getId());
			hospitalDto.setName(hospital.getName());
			hospitalDto.setLocation(hospital.getLocation());
			hospitalDto.setApprovedInsuranceCompanies(hospital.getApprovedInsuranceCompanies());
			hospitalDto.setContactPhone(hospital.getContactPhone());
			hospitalDto.setCreatedAt(hospital.getCreatedAt());
			return hospitalDto;
		}).collect(Collectors.toList());
	}
}