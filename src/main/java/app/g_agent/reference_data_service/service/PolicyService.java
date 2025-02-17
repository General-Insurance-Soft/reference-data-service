package app.g_agent.reference_data_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.g_agent.reference_data_service.dto.PolicyDto;
import app.g_agent.reference_data_service.model.Policy;
import app.g_agent.reference_data_service.repository.PolicyRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);

    private final PolicyRepository policyRepository;
    private JwtService jwtService;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
        this.jwtService = jwtService;
    }

    public Policy getPolicyById(Long id) throws Exception {
        Optional<Policy> policy = policyRepository.getPolicyById(id);
        if (policy.isPresent()) {
            return policy.get();
        } else {
            throw new Exception("The Policy does not exist");
        }
    }

    @Transactional
    public void createPolicy(HttpServletRequest request, PolicyDto policyDto) throws Exception {
        Long userId = (Long) jwtService.getTokenValue(jwtService.getJWT(request), "user-id");

        Policy policy = new Policy();
        policy.setName(policyDto.getName());
        policy.setUpdatedBy(userId);

        try {
            policyRepository.save(policy);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Policy error ==========> id: " + ex.getMessage());
                throw new Exception("This policy already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void updatePolicy(HttpServletRequest request, Long id, PolicyDto policyDto) throws Exception {
        Long userId = (Long) jwtService.getTokenValue(jwtService.getJWT(request), "user-id");
        Optional<Policy> policyOpt = policyRepository.getPolicyById(id);

        if (policyOpt.isEmpty()) {
            throw new Exception("The policy cannot be found");
        }

        Policy policy = policyOpt.get();
        policy.setName(policyDto.getName());
        policy.setUpdatedBy(userId);

        try {
            policyRepository.save(policy);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                logger.info("Policy error ==========> id: " + ex.getMessage());
                throw new Exception("This policy already exists.");
            }
            throw ex; // Rethrow if not related to constraint violation
        }
    }

    @Transactional
    public void deletePolicy(HttpServletRequest request, Long id) throws Exception {
        Optional<Policy> policyOpt = policyRepository.getPolicyById(id);

        if (policyOpt.isPresent()) {
            policyRepository.delete(policyOpt.get());
        } else {
            throw new Exception("The policy cannot be found");
        }
    }

    public PolicyDto getPolicy(HttpServletRequest request, Long id) throws Exception {
        Optional<Policy> policyOpt = policyRepository.getPolicyById(id);
        if (policyOpt.isPresent()) {
            Policy policy = policyOpt.get();
            PolicyDto policyDto = new PolicyDto();
            policyDto.setId(policy.getId());
            policyDto.setName(policy.getName());
            policyDto.setCreatedAt(policy.getCreatedAt());
            policyDto.setUpdatedAt(policy.getUpdatedAt());
            policyDto.setUpdatedBy(policy.getUpdatedBy());
            return policyDto;
        } else {
            throw new Exception("The Policy does not exist");
        }
    }

    public List<PolicyDto> getPolicies(HttpServletRequest request) throws Exception {
        List<Policy> policies = policyRepository.findAll();
        return policies.stream().map(policy -> {
            PolicyDto policyDto = new PolicyDto();
            policyDto.setId(policy.getId());
            policyDto.setName(policy.getName());
            policyDto.setCreatedAt(policy.getCreatedAt());
            policyDto.setUpdatedAt(policy.getUpdatedAt());
            policyDto.setUpdatedBy(policy.getUpdatedBy());
            return policyDto;
        }).collect(Collectors.toList());
    }
}