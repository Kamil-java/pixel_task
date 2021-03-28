package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.PractitionerRepository;
import pl.bak.pixel_task.model.Practitioner;

import java.util.List;

@Service
public class PractitionerService {
    private final PractitionerRepository practitionerRepository;

    public PractitionerService(PractitionerRepository practitionerRepository) {
        this.practitionerRepository = practitionerRepository;
    }

    public void savePractitioners(List<Practitioner> practitioners){
        practitionerRepository.saveAll(practitioners);
    }

    public List<Practitioner> practitioners(List<String> specializations){
        return practitionerRepository.findAllBySpecializationIn(specializations);
    }
}
