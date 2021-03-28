package test.util;

import org.springframework.stereotype.Component;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;

import java.time.LocalDateTime;

@Component
public class ObjectsToTests {

    public static Visit prepareVisit(){
        Visit visit = new Visit();
        visit.setVisitId(1L);
        visit.setPatientId(preparePatient());
        visit.setPractitionerId(preparePractitioner());
        return visit;
    }

    public static VisitDTO prepareVisitDto(){
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setVisitId(1L);
        visitDTO.setPatientId(1L);
        visitDTO.setPractitionerId(1L);
        return visitDTO;
    }

    public static Practitioner preparePractitioner() {
        Practitioner practitioner = new Practitioner();
        practitioner.setPractitionerId(1L);
        practitioner.setSpecialization("Cardiology");
        return practitioner;
    }

    public static Patient preparePatient(){
        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setCity("Kutno");
        patient.setFirstName("Paweł");
        patient.setLastName("Kowal");
        patient.setCreatedAt(LocalDateTime.of(2030,2,15,8,20,0));
        return patient;
    }
}
