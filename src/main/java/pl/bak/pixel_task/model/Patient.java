package pl.bak.pixel_task.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Patient")
@Table(
        name = "patient"
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "patientId"
    )
    private Long patientId;

    @Column(
            name = "firstName",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "lastName",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "city",
            nullable = false
    )
    private String city;

    @Column(
            name = "createdAt",
            nullable = false
    )
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patientId")
    private Set<Visit> visit = new HashSet<>();

    @ManyToMany
    private List<Practitioner> practitioners = new ArrayList<>();

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Visit> getVisit() {
        return visit;
    }

    public void setVisit(Set<Visit> visit) {
        this.visit = visit;
    }

    public List<Practitioner> getPractitioners() {
        return practitioners;
    }

    public void setPractitioners(List<Practitioner> practitioners) {
        this.practitioners = practitioners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(patientId, patient.patientId) && Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName) && Objects.equals(city, patient.city) && Objects.equals(createdAt, patient.createdAt) && Objects.equals(visit, patient.visit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, firstName, lastName, city, createdAt, visit);
    }
}

