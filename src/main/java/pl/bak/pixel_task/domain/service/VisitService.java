package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.VisitRepository;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Visit;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public void saveVisits(List<Visit> visits){
        visitRepository.saveAll(visits);
    }

    public int countVisit(Patient patientId){
        return visitRepository.countVisitByPatientId(patientId);
    }
}
