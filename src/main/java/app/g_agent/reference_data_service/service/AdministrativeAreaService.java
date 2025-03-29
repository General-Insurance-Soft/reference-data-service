package app.g_agent.reference_data_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.AdministrativeAreaDto;
import app.g_agent.reference_data_service.model.AdministrativeArea;
import app.g_agent.reference_data_service.repository.AdministrativeAreaRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdministrativeAreaService {

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeAreaService.class);

    private final AdministrativeAreaRepository administrativeAreaRepository;
    private final JwtService jwtService;

    public AdministrativeAreaService(AdministrativeAreaRepository administrativeAreaRepository, JwtService jwtService) {
        this.administrativeAreaRepository = administrativeAreaRepository;
        this.jwtService = jwtService;
    }

    public AdministrativeAreaDto getAdministrativeArea(HttpServletRequest request, Long id) throws Exception {
        Optional<AdministrativeArea> areaOpt = administrativeAreaRepository.findById(id);
        if (areaOpt.isPresent()) {
            AdministrativeArea area = areaOpt.get();
            return mapToDto(area);
        } else {
            throw new Exception("The Administrative Area does not exist");
        }
    }

    public List<AdministrativeAreaDto> getAdministrativeAreas(HttpServletRequest request) {
        List<AdministrativeArea> areas = administrativeAreaRepository.findAll();
        return areas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<AdministrativeAreaDto> getAdministrativeAreas(HttpServletRequest request, List<Long> locationIds) {
        List<AdministrativeArea> areas = administrativeAreaRepository.findByIds(locationIds);
	
		if (areas.isEmpty()) {
			List<AdministrativeAreaDto> emptyList = new ArrayList<>();
			return emptyList;
		}
        return areas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void createAdministrativeArea(HttpServletRequest request, AdministrativeAreaDto areaDto) throws Exception {
        AdministrativeArea area = new AdministrativeArea();
        Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());

        area.setCountryCode(areaDto.getCountryCode());
        area.setName(areaDto.getName());
        area.setLevel(areaDto.getLevel());
        area.setUpdatedBy(userId);

        if (areaDto.getParentId() != null) {
            Optional<AdministrativeArea> parentOpt = administrativeAreaRepository.findById(areaDto.getParentId());
            parentOpt.ifPresent(area::setParent);
        }

        try {
            administrativeAreaRepository.save(area);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error creating Administrative Area: {}", ex.getMessage());
            throw new Exception("An error occurred while creating the Administrative Area.");
        }
    }

    @Transactional
    public void updateAdministrativeArea(HttpServletRequest request, Long id, AdministrativeAreaDto areaDto) throws Exception {
        Optional<AdministrativeArea> areaOpt = administrativeAreaRepository.findById(id);
        Long userId = Long.parseLong(jwtService.getTokenValue(jwtService.getJWT(request), "user-id").toString());

        if (areaOpt.isEmpty()) {
            throw new Exception("The Administrative Area cannot be found");
        }

        AdministrativeArea area = areaOpt.get();
        area.setCountryCode(areaDto.getCountryCode());
        area.setName(areaDto.getName());
        area.setLevel(areaDto.getLevel());
        area.setUpdatedBy(userId);

        if (areaDto.getParentId() != null) {
            Optional<AdministrativeArea> parentOpt = administrativeAreaRepository.findById(areaDto.getParentId());
            parentOpt.ifPresent(area::setParent);
        }

        try {
            administrativeAreaRepository.save(area);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error updating Administrative Area: {}", ex.getMessage());
            throw new Exception("An error occurred while updating the Administrative Area.");
        }
    }

    @Transactional
    public void deleteAdministrativeArea(HttpServletRequest request, Long id) throws Exception {
        Optional<AdministrativeArea> areaOpt = administrativeAreaRepository.findById(id);

        if (areaOpt.isPresent()) {
            administrativeAreaRepository.delete(areaOpt.get());
        } else {
            throw new Exception("The Administrative Area cannot be found");
        }
    }

    private AdministrativeAreaDto mapToDto(AdministrativeArea area) {
        AdministrativeAreaDto dto = new AdministrativeAreaDto();
        dto.setId(area.getId());
        dto.setCountryCode(area.getCountryCode());
        dto.setName(area.getName());
        dto.setLevel(area.getLevel());
        dto.setParentId(area.getParent() != null ? area.getParent().getId() : null);
        dto.setCreatedAt(area.getCreatedAt());
        dto.setUpdatedAt(area.getUpdatedAt());
        dto.setUpdatedBy(area.getUpdatedBy());
        return dto;
    }
}