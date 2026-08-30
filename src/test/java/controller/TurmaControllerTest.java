package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import Dto.TurmaDto;
import jakarta.servlet.http.HttpSession;
import service.DiciplinaImplService;
import service.TurmaImplService;

@ExtendWith(MockitoExtension.class)
class TurmaControllerTest {

	@Mock
	private TurmaImplService turmaImplService;

	@Mock
	private DiciplinaImplService diciplinaImplService;

	@Mock
	private HttpSession session;

	@InjectMocks
	private TurmaController turmaController;

	@Test
	void testPesquisaporTurmas_Success() {
		List<TurmaDto> turmas = new ArrayList<>();
		when(turmaImplService.pesquisarTurmas("Turma A")).thenReturn(turmas);

		ModelAndView mv = turmaController.pesquisaporTurmas("Turma A");

		assertEquals("Turma/Turmas.html", mv.getViewName());
		assertEquals(turmas, mv.getModel().get("Turmas"));
	}

	@Test
	void testPesquisaporTurmas_ExceptionHandling() {
		when(turmaImplService.pesquisarTurmas(anyString())).thenThrow(new RuntimeException("Database error"));

		ModelAndView mv = turmaController.pesquisaporTurmas("Turma A");

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testListarTurma_Success() {
		List<TurmaDto> turmas = new ArrayList<>();
		when(turmaImplService.getDiciplina_Turma()).thenReturn(turmas);

		ModelAndView mv = turmaController.ListarTurma();

		assertEquals("Turma/Turmas.html", mv.getViewName());
		assertEquals(turmas, mv.getModel().get("Turmas"));
	}

	@Test
	void testListarTurma_ExceptionHandling() {
		when(turmaImplService.getDiciplina_Turma()).thenThrow(new RuntimeException("Error"));

		ModelAndView mv = turmaController.ListarTurma();

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testListarTurmaAlunos_Success() {
		List<TurmaDto> alunos = new ArrayList<>();
		when(turmaImplService.getDiciplina_TurmaID(1L)).thenReturn(alunos);

		ModelAndView mv = turmaController.ListarTurmaAlunos(1L);

		assertEquals("Turma/turma-alunos.html", mv.getViewName());
		assertEquals(alunos, mv.getModel().get("Alunos"));
	}

	@Test
	void testListarTurmaAlunos_ExceptionHandling() {
		when(turmaImplService.getDiciplina_TurmaID(1L)).thenThrow(new RuntimeException("Error"));

		ModelAndView mv = turmaController.ListarTurmaAlunos(1L);

		assertEquals("redirect:/error", mv.getViewName());
	}
}
