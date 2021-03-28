package pl.bak.pixel_task.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Visit")
@Table
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "visitId"
    )
    private Long visitId;

    @ManyToOne
    private Practitioner practitionerId;

    @ManyToOne
    private Patient patientId;


    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Practitioner getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Practitioner practitionerId) {
        this.practitionerId = practitionerId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }
}
