package pl.bak.pixel_task.domain.service;

import org.springframework.stereotype.Service;
import pl.bak.pixel_task.domain.dao.PractitionerRepository;
import pl.bak.pixel_task.model.Practitioner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PractitionerService {
    private final PractitionerRepository practitionerRepository;

    public PractitionerService(PractitionerRepository practitionerRepository) {
        this.practitionerRepository = practitionerRepository;
    }

    @Transactional
    public void savePractitioners(List<Practitioner> practitioners) {
        practitionerRepository.saveAll(practitioners);
    }

    public List<Practitioner> getListOfPracitionerIfParamIsEmptyOrEqualsAll(List<String> specializations) {
        if (!specializations.isEmpty()) {
            if (specializations.size() == 1 && specializations.get(0).equals("ALL")) {
                return practitionerRepository.findAll();
            }
            return practitionerRepository.findAllBySpecializationIn(specializations);
        }
        return practitionerRepository.findAll();
    }

    public Optional<String> specializationUnknownSearch(List<String> specializations) {
        return specializations
                .stream()
                .filter(this::specializationDoesntExist)
                .findFirst();
    }

    public boolean specializationDoesntExist(String specialization) {
        return !practitionerRepository.existsBySpecialization(specialization);
    }
}
