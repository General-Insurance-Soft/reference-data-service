package app.g_agent.reference_data_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.VehicleModelDto;
import app.g_agent.reference_data_service.model.VehicleMake;
import app.g_agent.reference_data_service.model.VehicleModel;
import app.g_agent.reference_data_service.repository.VehicleMakeRepository;
import app.g_agent.reference_data_service.repository.VehicleModelRepository;

@Service
public class VehicleModelService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleModelService.class);

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleMakeRepository vehicleMakeRepository;

    public VehicleModelService(VehicleModelRepository vehicleModelRepository, VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    public VehicleModel getVehicleModelById(Long id) throws Exception {
        Optional<VehicleModel> vehicleModel = vehicleModelRepository.getVehicleModelById(id);
        if (vehicleModel.isPresent()) {
            return vehicleModel.get();
        } else {
            throw new Exception("The Vehicle Model does not exist");
        }
    }

    @Transactional
    public void createVehicleModel(VehicleModelDto vehicleModelDto) throws Exception {
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setModel(vehicleModelDto.getModel());

        Optional<VehicleMake> vehicleMakeOpt = vehicleMakeRepository.getVehicleMakeById(vehicleModelDto.getMake().getId());
        if (vehicleMakeOpt.isEmpty()) {
            throw new Exception("The Vehicle Make does not exist");
        }
        vehicleModel.setMake(vehicleMakeOpt.get());

        try {
            vehicleModelRepository.save(vehicleModel);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Vehicle Model error ==========> id: " + ex.getMessage());
                throw new Exception("This vehicle model already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void updateVehicleModel(Long id, VehicleModelDto vehicleModelDto) throws Exception {
        Optional<VehicleModel> vehicleModelOpt = vehicleModelRepository.getVehicleModelById(id);

        if (vehicleModelOpt.isEmpty()) {
            throw new Exception("The vehicle model cannot be found");
        }

        VehicleModel vehicleModel = vehicleModelOpt.get();
        vehicleModel.setModel(vehicleModelDto.getModel());

        Optional<VehicleMake> vehicleMakeOpt = vehicleMakeRepository.getVehicleMakeById(vehicleModelDto.getMake().getId());
        if (vehicleMakeOpt.isEmpty()) {
            throw new Exception("The Vehicle Make does not exist");
        }
        vehicleModel.setMake(vehicleMakeOpt.get());

        try {
            vehicleModelRepository.save(vehicleModel);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Vehicle Model error ==========> id: " + ex.getMessage());
                throw new Exception("This vehicle model already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void deleteVehicleModel(Long id) throws Exception {
        Optional<VehicleModel> vehicleModelOpt = vehicleModelRepository.getVehicleModelById(id);

        if (vehicleModelOpt.isEmpty()) {
            throw new Exception("The vehicle model cannot be found");
        }

        vehicleModelRepository.delete(vehicleModelOpt.get());
    }
}