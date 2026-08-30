package controller;

import Dto.NotaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.AlunoImplService;
import service.NotaImplService;
import util.GeradordeRelatorio;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
	}

	@Test
	public void testListarNotasAlunoSuccess() throws Exception {
		Long alunoId = 1L;
		List<NotaDto> notas = Collections.singletonList(
				new NotaDto(8.5, "Aluno Teste", "Matematica", 9.0, 8.0, 8.5)
		);

		given(notaImplService.ListarImpressaoNotaAluno(alunoId)).willReturn(notas);

		mockMvc.perform(get("/listarNotas")
				.sessionAttr("aluno", alunoId))
				.andExpect(status().isOk())
				.andExpect(view().name("Notas/notas.html"))
				.andExpect(model().attribute("Notas", notas));

		verify(notaImplService, atLeastOnce()).ListarImpressaoNotaAluno(alunoId);
	}

	@Test
	public void testListarNotasAlunoErrorScenario() throws Exception {
		Long alunoId = 1L;
		given(notaImplService.ListarImpressaoNotaAluno(alunoId)).willThrow(new RuntimeException("Database error"));

		mockMvc.perform(get("/listarNotas")
				.sessionAttr("aluno", alunoId))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/error"));
	}
}
