package controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import Dto.NotaDto;
import service.AlunoImplService;
import service.NotaImplService;
import util.GeradordeRelatorio;

@ExtendWith(MockitoExtension.class)
public class AlunoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AlunoImplService alunoImplService;

	@Mock
	private NotaImplService notaImplService;

	@Mock
	private GeradordeRelatorio geradordeRelatorio;

	@InjectMocks
	private AlunoController alunoController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
	}

	@Test
	@DisplayName("ListarNotasAluno should render Notas/notas.html when service succeeds")
	void testListarNotasAluno_Success() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("aluno", 1L);

		List<NotaDto> notas = Collections.emptyList();
		when(notaImplService.ListarImpressaoNotaAluno(1L)).thenReturn(notas);

		mockMvc.perform(get("/listarNotas").session(session))
				.andExpect(view().name("Notas/notas.html"))
				.andExpect(model().attribute("Notas", notas));
	}

	@Test
	@DisplayName("ListarNotasAluno should redirect to /error when an exception is thrown")
	void testListarNotasAluno_Exception_RedirectsToError() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("aluno", 1L);

		when(notaImplService.ListarImpressaoNotaAluno(1L))
				.thenThrow(new RuntimeException("Database error or service failure"));

		mockMvc.perform(get("/listarNotas").session(session))
				.andExpect(view().name("redirect:/error"))
				.andExpect(redirectedUrl("/error"));
	}
}
