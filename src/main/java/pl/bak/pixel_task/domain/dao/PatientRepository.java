package pl.bak.pixel_task.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.pixel_task.model.Patient;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PatientRepository extends JpaRepository<Patient, Long> {

//    @Query("SELECT p FROM Patient p INNER JOIN Practitioner pr WHERE p.city = :cityName")
    List<Patient> findAllByCityIn(List<String> cityName);
}
