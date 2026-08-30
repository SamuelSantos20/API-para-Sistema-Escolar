package controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import service.AlunoImplService;
import service.DiciplinaImplService;
import service.NotaImplService;
import service.ProfessorImplService;
import util.GeradordeRelatorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessorControllerTest {

	@Mock
	private AlunoImplService alunoImplService;

	@Mock
	private DiciplinaImplService diciplinaImplService;

	@Mock
	private NotaImplService notaImplService;

	@Mock
	private GeradordeRelatorio geradordeRelatorio;

	@Mock
	private ProfessorImplService professorImplService;

	@InjectMocks
	private ProfessorController professorController;

	@Test
	void testPesquisarAlunoSuccess() {
		MockHttpSession session = new MockHttpSession();
		when(notaImplService.BuscarPorAluno("João")).thenReturn(java.util.Collections.emptyList());

		ModelAndView mv = professorController.PesquisarAluno("João", session);

		assertEquals("Relatorios/relatoriopesquisa.html", mv.getViewName());
		assertEquals("João", session.getAttribute("texto"));
	}

	@Test
	void testPesquisarAlunoException() {
		MockHttpSession session = new MockHttpSession();
		when(notaImplService.BuscarPorAluno(anyString())).thenThrow(new RuntimeException("Database error"));

		ModelAndView mv = professorController.PesquisarAluno("João", session);

		assertEquals("redirect:/error", mv.getViewName());
	}
}
