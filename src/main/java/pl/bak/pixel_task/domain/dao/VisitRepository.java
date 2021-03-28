package pl.bak.pixel_task.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findDistinctByPractitionerIdInAndPatientIdIn(List<Practitioner> practitionerId, List<Patient> patientId);

    long countVisitByPatientId(Patient patientId);

    long countVisitByPractitionerId(Practitioner practitionerId);
}
