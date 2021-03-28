package pl.bak.pixel_task.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bak.pixel_task.domain.dao.VisitRepository;
import pl.bak.pixel_task.dto.SpecializationResultDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;
import pl.bak.pixel_task.util.SimpleMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static test.util.ObjectsToTests.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PractitionerService practitionerService;

    @Mock
    private SimpleMapper simpleMapper;

    private VisitService visitService;

    @BeforeEach
    void setUp() {
        visitService = new VisitService(visitRepository, practitionerService, simpleMapper);
    }

    @Test
    void saveVisits() {
        //given
        Visit prepareVisit = prepareVisit();
        List<Visit> visits = Collections.singletonList(prepareVisit);

        given(visitRepository.saveAll(visits))
                .willReturn(visits);

        //when
        visitService.saveVisits(visits);

        //then
        verify(visitRepository).saveAll(visits);
    }

    @Test
    void shouldGetNumberOfVisitForSpecialization() {
        //given
        given(practitionerService.specializationDoesntExist("Cardiology")).willReturn(false);

        given(practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(Collections.singletonList("Cardiology")))
                .willReturn(Collections.singletonList(preparePractitioner()));

        given(visitRepository.countVisitByPractitionerId(preparePractitioner())).willReturn(3L);

        //when
        Optional<SpecializationResultDTO> cardiology = visitService.getNumberOfVisitForSpecialization("Cardiology");

        //then
        assertThat(cardiology.isPresent()).isTrue();
        assertThat(cardiology.get())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("visit")
                .hasFieldOrProperty("numberOfVisit");

    }

    @Test
    void shouldReturnEmptyOptional() {
        //given
        given(practitionerService.specializationDoesntExist("Cardio")).willReturn(true);

        //when
        Optional<SpecializationResultDTO> cardiology = visitService.getNumberOfVisitForSpecialization("Cardio");

        //then
        assertThat(cardiology.isPresent()).isFalse();
    }

    @Test
    void shouldReturnListOfDtoVisits() {
        //given
        List<Practitioner> practitionerId = Collections.singletonList(preparePractitioner());
        List<Patient> patientId = Collections.singletonList(preparePatient());
        List<Visit> visits = Collections.singletonList(prepareVisit());
        given(visitRepository.findDistinctByPractitionerIdInAndPatientIdIn(
                practitionerId,
                patientId
        )).willReturn(visits);

        given(simpleMapper.mapListVisitToDto(visits))
                .willReturn(Collections.singletonList(prepareVisitDto()));

        //when
        List<VisitDTO> visitDTOS = visitService.visitList(practitionerId, patientId);

        //then
        assertThat(visitDTOS)
                .isNotNull()
                .asList()
                .hasSize(1)
                .isNotEmpty();
    }

    @Test
    void shouldReturnNumberOfAllVisitForPatient() {
        //given
        Patient patientId = preparePatient();
        given(visitRepository.countVisitByPatientId(patientId)).willReturn(30L);

        //when
        long countVisit = visitService.countVisitByPatientId(patientId);

        //then
        assertThat(countVisit)
                .isPositive()
                .isEqualTo(30L)
                .isEven()
                .isGreaterThan(29L)
                .isLessThan(31L);
    }
}