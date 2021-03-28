package pl.bak.pixel_task.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Practitioner")
@Table(
        name = "practitioner"
)
public class Practitioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "practitionerId"
    )
    private Long practitionerId;

    @Column(
            name = "specialization",
            nullable = false
    )
    private String specialization;

    @OneToMany(mappedBy = "practitionerId")
    private Set<Visit> visit = new HashSet<>();

    public Long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Long practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<Visit> getVisit() {
        return visit;
    }

    public void setVisit(Set<Visit> visit) {
        this.visit = visit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Practitioner that = (Practitioner) o;
        return Objects.equals(practitionerId, that.practitionerId) && Objects.equals(specialization, that.specialization) && Objects.equals(visit, that.visit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(practitionerId, specialization, visit);
    }
}
