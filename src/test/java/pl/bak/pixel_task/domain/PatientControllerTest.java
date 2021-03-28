package pl.bak.pixel_task.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.bak.pixel_task.domain.service.PatientService;
import pl.bak.pixel_task.domain.service.PractitionerService;
import pl.bak.pixel_task.dto.ResultDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@AutoConfigureRestDocs(outputDir = "documentation/endpoints")
@AutoConfigureMockMvc
class PatientControllerTest {

    @MockBean
    private PatientService patientService;

    @MockBean
    private PractitionerService practitionerService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void patientDataOfVisits() throws Exception {
        //given
        given(patientService.cityUnknownSearch(Arrays.asList("Łódź","Kutno"))).willReturn(Optional.empty());

        given(practitionerService.specializationUnknownSearch(Collections.singletonList("Pediatrics"))).willReturn(Optional.empty());

        given(patientService.getAllResults(Arrays.asList("Łódź","Kutno"), Collections.singletonList("Pediatrics")))
                .willReturn(Arrays.asList(
                        prepareResultDto("Adam", "Kowal", 3),
                        prepareResultDto("Jan", "Nowak", 7)
                ));

        //when
        ResultActions result = mockMvc.perform(get("/patient/visit")
                .param("cities", "Łódź", "Kutno")
                .param("specialities", "Pediatrics")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0]").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").exists())
                .andExpect(jsonPath("$.[0].firstName").isString())
                .andExpect(jsonPath("$.[0].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").value("Adam"))
                .andExpect(jsonPath("$.[0].lastName").exists())
                .andExpect(jsonPath("$.[0].lastName").exists())
                .andExpect(jsonPath("$.[0].lastName").isString())
                .andExpect(jsonPath("$.[0].lastName").isNotEmpty())
                .andExpect(jsonPath("$.[0].lastName").value("Kowal"))
                .andExpect(jsonPath("$.[0].countVisits").exists())
                .andExpect(jsonPath("$.[0].countVisits").isNumber())
                .andExpect(jsonPath("$.[0].countVisits").value(3))
                .andExpect(jsonPath("$.[1]").exists())
                .andDo(print())
                .andDo(document("patient-visits"));
    }

    @Test
    public void shouldThrowException404IfCityOrSpecializationDoesntExist() throws Exception {
        //given
        given(patientService.cityUnknownSearch(Arrays.asList("Łódź","Kutn"))).willReturn(Optional.of(" "));

        given(practitionerService.specializationUnknownSearch(Collections.singletonList("Pediatri"))).willReturn(Optional.of(" "));

        //when
        ResultActions result = mockMvc.perform(get("/patient/visit")
                .param("cities", "Łódź", "Kutn")
                .param("specialities", "Pediatri")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andDo(document("unknown-city-or-specialization"));
    }

    @Test
    public void shouldReturnEmptyArrayAndThrowNoContent() throws Exception {
        //given
        given(patientService.cityUnknownSearch(Arrays.asList("Łódź","Kutno"))).willReturn(Optional.empty());

        given(practitionerService.specializationUnknownSearch(Collections.singletonList("Pediatrics"))).willReturn(Optional.empty());

        given(patientService.getAllResults(Collections.emptyList(), Collections.emptyList()))
                .willReturn(Collections.emptyList());

        //when
        ResultActions result = mockMvc.perform(get("/patient/visit")
                .param("cities", "")
                .param("specialities", "")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andDo(document("get-empty-list"));
    }

    @Test
    public void shouldReturnArrayAllForEmptyQueryCitiesAndSpecializations() throws Exception {
        //given
        given(patientService.cityUnknownSearch(Arrays.asList("Łódź","Kutno"))).willReturn(Optional.empty());

        given(practitionerService.specializationUnknownSearch(Collections.singletonList("Pediatrics"))).willReturn(Optional.empty());

        given(patientService.getAllResults(Collections.emptyList(), Collections.emptyList()))
                .willReturn(Arrays.asList(
                        prepareResultDto("Adam", "Kowal", 3),
                        prepareResultDto("Jan", "Nowak", 7)
                ));

        //when
        ResultActions result = mockMvc.perform(get("/patient/visit")
                .param("cities", "")
                .param("specialities", "")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0]").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").exists())
                .andExpect(jsonPath("$.[0].firstName").isString())
                .andExpect(jsonPath("$.[0].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").value("Adam"))
                .andExpect(jsonPath("$.[0].lastName").exists())
                .andExpect(jsonPath("$.[0].lastName").exists())
                .andExpect(jsonPath("$.[0].lastName").isString())
                .andExpect(jsonPath("$.[0].lastName").isNotEmpty())
                .andExpect(jsonPath("$.[0].lastName").value("Kowal"))
                .andExpect(jsonPath("$.[0].countVisits").exists())
                .andExpect(jsonPath("$.[0].countVisits").isNumber())
                .andExpect(jsonPath("$.[0].countVisits").value(3))
                .andExpect(jsonPath("$.[1]").exists())
                .andDo(print())
                .andDo(document("get-a-list-with-no-request-parameters"));
    }

    private ResultDTO prepareResultDto(String name, String lastName, long countVisit){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setFirstName(name);
        resultDTO.setLastName(lastName);
        resultDTO.setCountVisits(countVisit);
        return resultDTO;
    }
}