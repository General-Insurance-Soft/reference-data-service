package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.VehicleMakeDto;
import app.g_agent.reference_data_service.model.VehicleMake;
import app.g_agent.reference_data_service.repository.VehicleMakeRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class VehicleMakeService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleMakeService.class);

    private final VehicleMakeRepository vehicleMakeRepository;

    public VehicleMakeService(VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    public VehicleMake getVehicleMakeById(Long id) throws Exception {
        Optional<VehicleMake> vehicleMake = vehicleMakeRepository.getVehicleMakeById(id);
        if (vehicleMake.isPresent()) {
            return vehicleMake.get();
        } else {
            throw new Exception("The Vehicle Make does not exist");
        }
    }

    @Transactional
    public void createVehicleMake(HttpServletRequest request, VehicleMakeDto vehicleMakeDto) throws Exception {
        VehicleMake vehicleMake = new VehicleMake();
        vehicleMake.setMake(vehicleMakeDto.getMake());

        try {
            vehicleMakeRepository.save(vehicleMake);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Vehicle Make error ==========> id: " + ex.getMessage());
                throw new Exception("This vehicle make already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void updateVehicleMake(HttpServletRequest request, Long id, VehicleMakeDto vehicleMakeDto) throws Exception {
        Optional<VehicleMake> vehicleMakeOpt = vehicleMakeRepository.getVehicleMakeById(id);

        if (vehicleMakeOpt.isEmpty()) {
            throw new Exception("The vehicle make cannot be found");
        }

        VehicleMake vehicleMake = vehicleMakeOpt.get();
        vehicleMake.setMake(vehicleMakeDto.getMake());

        try {
            vehicleMakeRepository.save(vehicleMake);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Vehicle Make error ==========> id: " + ex.getMessage());
                throw new Exception("This vehicle make already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void deleteVehicleMake(HttpServletRequest request, Long id) throws Exception {
        Optional<VehicleMake> vehicleMakeOpt = vehicleMakeRepository.getVehicleMakeById(id);

        if (vehicleMakeOpt.isPresent()) {
            vehicleMakeRepository.delete(vehicleMakeOpt.get());
        } else {
            throw new Exception("The vehicle make cannot be found");
        }
    }

    public VehicleMakeDto getVehicleMake(HttpServletRequest request, Long id) throws Exception {
        Optional<VehicleMake> vehicleMakeOpt = vehicleMakeRepository.getVehicleMakeById(id);
        if (vehicleMakeOpt.isPresent()) {
            VehicleMake vehicleMake = vehicleMakeOpt.get();
            VehicleMakeDto vehicleMakeDto = new VehicleMakeDto();
            vehicleMakeDto.setId(vehicleMake.getId());
            vehicleMakeDto.setMake(vehicleMake.getMake());
            return vehicleMakeDto;
        } else {
            throw new Exception("The Vehicle Make does not exist");
        }
    }

    public List<VehicleMakeDto> getVehicleMakes(HttpServletRequest request) throws Exception {
        List<VehicleMake> vehicleMakes = vehicleMakeRepository.findAll();
        return vehicleMakes.stream().map(vehicleMake -> {
            VehicleMakeDto vehicleMakeDto = new VehicleMakeDto();
            vehicleMakeDto.setId(vehicleMake.getId());
            vehicleMakeDto.setMake(vehicleMake.getMake());
            return vehicleMakeDto;
        }).collect(Collectors.toList());
    }
}