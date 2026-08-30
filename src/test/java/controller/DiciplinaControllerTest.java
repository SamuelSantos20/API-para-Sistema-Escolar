package controller;

import com.example.demo.TooDevs.br.ApiParaBoletimEscolarApplication;
import domain.Abrangencia;
import domain.Diciplina;
import domain.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import service.DiciplinaImplService;
import service.ProfessorImplService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = ApiParaBoletimEscolarApplication.class)
@AutoConfigureMockMvc
class DiciplinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiciplinaImplService diciplinaImplService;

    @MockBean
    private ProfessorImplService professorImplService;

    @Test
    @DisplayName("RequestAddDiciplina should return addDiciplina view when user has ADM abrangencia")
    void requestAddDiciplina_whenUserIsADM_shouldReturnAddDiciplinaView() throws Exception {
        List<Professor> professors = Collections.singletonList(new Professor());
        when(professorImplService.ListarTodos()).thenReturn(professors);

        mockMvc.perform(get("/addDiciplina").sessionAttr("abrangencia", Abrangencia.ADM))
                .andExpect(status().isOk())
                .andExpect(view().name("Diciplinas/adicionar-diciplinas.html"))
                .andExpect(model().attribute("professor", professors))
                .andExpect(model().attribute("Diciplina", instanceOf(Diciplina.class)));
    }

    @Test
    @DisplayName("RequestAddDiciplina should return indexADM view with error message when user has PROFESSOR abrangencia")
    void requestAddDiciplina_whenUserIsProfessor_shouldReturnIndexADMViewWithErrorMessage() throws Exception {
        mockMvc.perform(get("/addDiciplina").sessionAttr("abrangencia", Abrangencia.PROFESSOR))
                .andExpect(status().isOk())
                .andExpect(view().name("indexADM.html"))
                .andExpect(model().attribute("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM "));
    }

    @Test
    @DisplayName("RequestAddDiciplina should return indexADM view with error message when user has ALUNO abrangencia")
    void requestAddDiciplina_whenUserIsAluno_shouldReturnIndexADMViewWithErrorMessage() throws Exception {
        mockMvc.perform(get("/addDiciplina").sessionAttr("abrangencia", Abrangencia.ALUNO))
                .andExpect(status().isOk())
                .andExpect(view().name("indexADM.html"))
                .andExpect(model().attribute("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM "));
    }

    @Test
    @DisplayName("RequestAddDiciplina should return indexADM view with error message when session has no abrangencia attribute")
    void requestAddDiciplina_whenSessionHasNoAbrangencia_shouldReturnIndexADMViewWithErrorMessage() throws Exception {
        mockMvc.perform(get("/addDiciplina"))
                .andExpect(status().isOk())
                .andExpect(view().name("indexADM.html"))
                .andExpect(model().attribute("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM "));
    }

    @Test
    @DisplayName("RequestAddDiciplina should redirect to error view when service throws an exception")
    void requestAddDiciplina_whenServiceThrowsException_shouldRedirectToError() throws Exception {
        when(professorImplService.ListarTodos()).thenThrow(new RuntimeException("Database connection error"));

        mockMvc.perform(get("/addDiciplina").sessionAttr("abrangencia", Abrangencia.ADM))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/error"));
    }
}
