package pl.bak.pixel_task.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Visit;

@Repository
@Transactional(readOnly = true)
public interface VisitRepository extends JpaRepository<Visit, Long> {

    int countVisitByPatientId(Patient patientId);
}
