package pl.bak.pixel_task.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bak.pixel_task.domain.dao.PatientRepository;
import pl.bak.pixel_task.dto.ResultDTO;
import pl.bak.pixel_task.dto.VisitDTO;
import pl.bak.pixel_task.model.Patient;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.util.SimpleMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static test.util.ObjectsToTests.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private VisitService visitService;

    @Mock
    private PractitionerService practitionerService;

    @Mock
    private SimpleMapper simpleMapper;

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientService = new PatientService(patientRepository, visitService, practitionerService, simpleMapper);
    }

    @Test
    void shouldSavePatients() {
        //given
        List<Patient> patients = Collections.singletonList(preparePatient());
        given(patientRepository.saveAll(patients)).willReturn(patients);

        //when
        patientService.savePatients(patients);

        //then
        verify(patientRepository).saveAll(patients);
    }

    @Test
    void shouldReturnAllRequiredResults() {
        //given
        List<Practitioner> practitionerId = Collections.singletonList(preparePractitioner());
        List<Patient> patientId = Collections.singletonList(preparePatient());
        List<VisitDTO> visitDTOS = Collections.singletonList(prepareVisitDto());
        List<String> cardiology = Collections.singletonList("Cardiology");
        List<String> cities = Collections.singletonList("Kutno");

        given(visitService.visitList(practitionerId, patientId)).willReturn(visitDTOS);
        given(practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(cardiology))
                .willReturn(practitionerId);
        given(patientRepository.findAllByCityIn(cities))
                .willReturn(patientId);
        given(visitService.countVisitByPatientId(preparePatient())).willReturn(3L);
        given(simpleMapper.mapObjectToResultDto(preparePatient(), 3L)).willReturn(prepareResultDTO());

        //when
        List<ResultDTO> allResults = patientService.getAllResults(cities, cardiology);

        //then
        assertThat(allResults)
                .isNotNull()
                .asList()
                .hasSize(1);
        assertThat(allResults.get(0))
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("firstName")
                .hasFieldOrProperty("lastName")
                .hasFieldOrProperty("countVisits");

    }

    @Test
    void shouldReturnOptionalEmptyIfCityIsKnownAndReturnOptionalIfCityIsUnKnown() {
        //given
        given(patientRepository.existsByCity("Kutno")).willReturn(false);
        given(patientRepository.existsByCity("Kut")).willReturn(true);

        //when
        Optional<String> kutno = patientService.cityUnknownSearch(Collections.singletonList("Kutno"));
        Optional<String> kut = patientService.cityUnknownSearch(Collections.singletonList("Kut"));

        //then
        assertThat(kutno.isPresent())
                .isTrue();
        assertThat(kut.isPresent())
                .isFalse();

    }

    private ResultDTO prepareResultDTO(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setFirstName("Paweł");
        resultDTO.setLastName("Kowal");
        resultDTO.setCountVisits(3L);

        return resultDTO;
    }
}