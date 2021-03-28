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
import pl.bak.pixel_task.domain.service.VisitService;
import pl.bak.pixel_task.dto.SpecializationResultDTO;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
@AutoConfigureRestDocs(outputDir = "documentation/endpoints")
@AutoConfigureMockMvc
class VisitControllerTest {

    @MockBean
    private VisitService visitService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNameOfSpecializationAndNumberOfVisit() throws Exception {
        //given
        given(visitService.getNumberOfVisitForSpecialization("Cardiology")).willReturn(Optional.of(prepareResultDTO()));

        //when
        ResultActions visitResult = mockMvc.perform(get("/visit/number")
                .param("specialization", "Cardiology")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        visitResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.visit").exists())
                .andExpect(jsonPath("$.visit").isString())
                .andExpect(jsonPath("$.visit").isNotEmpty())
                .andExpect(jsonPath("$.visit").value("Cardiology"))
                .andExpect(jsonPath("$.numberOfVisit").exists())
                .andExpect(jsonPath("$.numberOfVisit").isNumber())
                .andExpect(jsonPath("$.numberOfVisit").value(101))
                .andDo(print())
                .andDo(document("visit-count-by-specialization"));
    }

    @Test
    void shouldReturnException404() throws Exception {
        //given
        given(visitService.getNumberOfVisitForSpecialization("Cardio")).willReturn(Optional.empty());

        //when
        ResultActions visitResult = mockMvc.perform(get("/visit/number")
                .param("specialization", "Cardio")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        visitResult
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andDo(document("visit-count-exception"));
    }

    private SpecializationResultDTO prepareResultDTO(){
       return new SpecializationResultDTO("Cardiology", 101);

    }
}