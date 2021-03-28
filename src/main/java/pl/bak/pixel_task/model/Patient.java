package pl.bak.pixel_task.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

}
