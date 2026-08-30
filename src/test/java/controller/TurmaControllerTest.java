package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Turma;
import jakarta.servlet.http.HttpSession;
import service.DiciplinaImplService;
import service.TurmaImplService;

@ExtendWith(MockitoExtension.class)
public class TurmaControllerTest {

	@Mock
	private TurmaImplService turmaImplService;

	@Mock
	private DiciplinaImplService diciplinaImplService;

	@Mock
	private HttpSession session;

	@InjectMocks
	private TurmaController turmaController;

	@BeforeEach
	void setUp() {
	}

	@Test
	void testRequestAddTurma_AdminAccess() {
		when(session.getAttribute("abrangencia")).thenReturn(Abrangencia.ADM);
		when(diciplinaImplService.ListarTodas()).thenReturn(new ArrayList<>());

		ModelAndView mv = turmaController.RequestAddTurma(session);

		assertEquals("Turma/AddTurma.html", mv.getViewName());
		assertNotNull(mv.getModel().get("Turma"));
		assertNotNull(mv.getModel().get("codigo"));
		assertNotNull(mv.getModel().get("diciplinas"));
	}

	@Test
	void testRequestAddTurma_NonAdminAccess() {
		when(session.getAttribute("abrangencia")).thenReturn(null);

		ModelAndView mv = turmaController.RequestAddTurma(session);

		assertEquals("redirect:/index", mv.getViewName());
		assertNotNull(mv.getModel().get("errorMessage"));
	}

	@Test
	void testAdicionarTurma_AdminSuccess() {
		when(session.getAttribute("abrangencia")).thenReturn(Abrangencia.ADM);
		when(diciplinaImplService.ListarTodas()).thenReturn(new ArrayList<>());

		Turma turma = new Turma();
		turma.setDiciplinas(new ArrayList<>());

		ModelAndView mv = turmaController.AdicionarTurma(turma, 1L, "TRM123", session);

		assertEquals("Turma/AddTurma.html", mv.getViewName());
		assertNotNull(mv.getModel().get("successMessage"));
		verify(turmaImplService).Salvar(any(Turma.class));
	}

	@Test
	void testListarTurma() {
		when(turmaImplService.getDiciplina_Turma()).thenReturn(new ArrayList<>());

		ModelAndView mv = turmaController.ListarTurma();

		assertEquals("Turma/Turmas.html", mv.getViewName());
		assertNotNull(mv.getModel().get("Turmas"));
		assertNotNull(mv.getModel().get("Turma"));
	}

	@Test
	void testPesquisarTurmas() {
		when(turmaImplService.pesquisarTurmas("Matematica")).thenReturn(new ArrayList<>());

		ModelAndView mv = turmaController.pesquisaporTurmas("Matematica");

		assertEquals("Turma/Turmas.html", mv.getViewName());
		assertNotNull(mv.getModel().get("Turmas"));
		assertNotNull(mv.getModel().get("Turma"));
	}

	@Test
	void testListarTurmaAlunos() {
		when(turmaImplService.getDiciplina_TurmaID(1L)).thenReturn(new ArrayList<>());

		ModelAndView mv = turmaController.ListarTurmaAlunos(1L);

		assertEquals("Turma/turma-alunos.html", mv.getViewName());
		assertNotNull(mv.getModel().get("Alunos"));
	}

	@Test
	void testListadeDiciplinasdoAluno() {
		when(session.getAttribute("aluno")).thenReturn(10L);
		when(turmaImplService.ListarAsDiciplinaseTurmas(10L)).thenReturn(new ArrayList<>());

		ModelAndView mv = turmaController.ListadeDiciplinasdoAluno(session);

		assertEquals("Diciplinas/diciplinasAluno.html", mv.getViewName());
		assertNotNull(mv.getModel().get("TurmaDiciplina"));
	}

	@Test
	void testListarTurma_ExceptionHandling() {
		when(turmaImplService.getDiciplina_Turma()).thenThrow(new RuntimeException("Database error"));

		ModelAndView mv = turmaController.ListarTurma();

		assertEquals("redirect:/error", mv.getViewName());
	}
}
