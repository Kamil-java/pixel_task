package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.VisitRepository;
import pl.bak.pixel_task.dto.SpecializationResultDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;
import pl.bak.pixel_task.util.SimpleMapper;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final PractitionerService practitionerService;
    private final SimpleMapper simpleMapper;

    public VisitService(VisitRepository visitRepository, PractitionerService practitionerService, SimpleMapper simpleMapper) {
        this.visitRepository = visitRepository;
        this.practitionerService = practitionerService;
        this.simpleMapper = simpleMapper;
    }

    @Transactional
    public void saveVisits(List<Visit> visits) {
        visitRepository.saveAll(visits);
    }

    public Optional<SpecializationResultDTO> getNumberOfVisitForSpecialization(String specialization) {
        if (practitionerService.specializationDoesntExist(specialization)){
             return Optional.empty();
        }
        List<Practitioner> practitioners = practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(Collections.singletonList(specialization));
        long numberOfVisits = visitRepository.countVisitByPractitionerId(practitioners.get(0));

        return Optional.of(new SpecializationResultDTO(specialization, numberOfVisits));
    }

    protected List<VisitDTO> visitList(List<Practitioner> practitionerId, List<Patient> patientId) {
        List<Visit> result = visitRepository.findDistinctByPractitionerIdInAndPatientIdIn(practitionerId, patientId);

        return simpleMapper.mapListVisitToDto(result);
    }

    protected long countVisitByPatientId(Patient patientId) {
        return visitRepository.countVisitByPatientId(patientId);
    }
}
