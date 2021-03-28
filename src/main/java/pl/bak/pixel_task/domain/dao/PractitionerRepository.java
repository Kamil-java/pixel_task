package pl.bak.pixel_task.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.pixel_task.model.Practitioner;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {

    List<Practitioner> findAllBySpecializationIn(List<String> specializations);
}
