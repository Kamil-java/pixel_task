package pl.bak.pixel_task.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bak.pixel_task.domain.dao.PractitionerRepository;
import pl.bak.pixel_task.model.Practitioner;
import pl.bak.pixel_task.model.Visit;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static test.util.ObjectsToTests.preparePractitioner;
import static test.util.ObjectsToTests.prepareVisit;

@ExtendWith(MockitoExtension.class)
class PractitionerServiceTest {

    @Mock
    private PractitionerRepository practitionerRepository;

    private PractitionerService practitionerService;

    @BeforeEach
    void setUp() {
        practitionerService = new PractitionerService(practitionerRepository);
    }

    @Test
    void shouldSavePractitioners() {
        //given
        List<Practitioner> practitioners = Collections.singletonList(preparePractitioner());
        given(practitionerRepository.saveAll(practitioners))
                .willReturn(practitioners);

        //when
        practitionerService.savePractitioners(practitioners);

        //then
        verify(practitionerRepository).saveAll(practitioners);
    }

    @Test
    void getListOfPracitionerIfParamIsEmptyOrEqualsAll() {
        //given
        List<Practitioner> practitioners = Collections.singletonList(preparePractitioner());
        List<String> cardiology = Collections.singletonList("Cardiology");

        given(practitionerRepository.findAll()).willReturn(practitioners);
        given(practitionerRepository.findAllBySpecializationIn(cardiology))
                .willReturn(practitioners);

        //when
        List<Practitioner> resultList = practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(cardiology);
        List<Practitioner> all = practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(Collections.singletonList("ALL"));
        List<Practitioner> empty = practitionerService.getListOfPracitionerIfParamIsEmptyOrEqualsAll(Collections.emptyList());

        //then
        assertConditional(resultList);
        assertConditional(all);
        assertConditional(empty);

    }

    private void assertConditional(List<Practitioner> list) {
        assertThat(list)
                .isNotNull()
                .asList()
                .hasSize(1);
        assertThat(list.get(0))
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("practitionerId")
                .hasFieldOrProperty("specialization");
    }

    @Test
    void shouldReturnOptionalIfSpecializationExist() {
        //given
        given(practitionerRepository.existsBySpecialization("Cardiology")).willReturn(false);

        //when
        Optional<String> result = practitionerService.specializationUnknownSearch(Collections.singletonList("Cardiology"));

        //then
        assertThat(result.isPresent())
                .isTrue();
    }

    @Test
    void shouldReturnFalseIfSpecializationExistAndReturnTrueIfDoesntExist() {
        //given
        given(practitionerRepository.existsBySpecialization("Cardiology")).willReturn(true);
        given(practitionerRepository.existsBySpecialization("Cardio")).willReturn(false);

        //when
        boolean resultExist = practitionerService.specializationDoesntExist("Cardiology");
        boolean resultDoesntExist = practitionerService.specializationDoesntExist("Cardio");

        //then
        assertThat(resultExist)
                .isFalse();
        assertThat(resultDoesntExist)
                .isTrue();
    }
}