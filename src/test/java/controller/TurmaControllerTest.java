package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Turma;
import service.DiciplinaImplService;
import service.TurmaImplService;

@ExtendWith(MockitoExtension.class)
class TurmaControllerTest {

	@Mock
	private TurmaImplService turmaImplService;

	@Mock
	private DiciplinaImplService diciplinaImplService;

	@InjectMocks
	private TurmaController turmaController;

	private MockHttpSession session;

	@BeforeEach
	void setUp() {
		session = new MockHttpSession();
	}

	@Test
	void testRequestAddTurmaSuccess() {
		session.setAttribute("abrangencia", Abrangencia.ADM);
		when(diciplinaImplService.ListarTodas()).thenReturn(Collections.emptyList());

		ModelAndView mv = turmaController.RequestAddTurma(session);

		assertEquals("Turma/AddTurma.html", mv.getViewName());
		assertNotNull(mv.getModel().get("Turma"));
		assertNotNull(mv.getModel().get("codigo"));
	}

	@Test
	void testRequestAddTurmaNonAdm() {
		session.setAttribute("abrangencia", Abrangencia.ALUNO);

		ModelAndView mv = turmaController.RequestAddTurma(session);

		assertEquals("redirect:/index", mv.getViewName());
	}

	@Test
	void testRequestAddTurmaException() {
		session.setAttribute("abrangencia", Abrangencia.ADM);
		when(diciplinaImplService.ListarTodas()).thenThrow(new RuntimeException("Database error"));

		ModelAndView mv = turmaController.RequestAddTurma(session);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testAdicionarTurmaSuccess() {
		session.setAttribute("abrangencia", Abrangencia.ADM);
		Turma turma = new Turma();
		when(diciplinaImplService.ListarTodas()).thenReturn(Collections.emptyList());

		ModelAndView mv = turmaController.AdicionarTurma(turma, 1L, "CODE123", session);

		assertEquals("Turma/AddTurma.html", mv.getViewName());
		assertEquals("Turma adicionada ao Sistema!.", mv.getModel().get("successMessage"));
	}

	@Test
	void testAdicionarTurmaException() {
		session.setAttribute("abrangencia", Abrangencia.ADM);
		Turma turma = new Turma();
		doThrow(new RuntimeException("Save error")).when(turmaImplService).Salvar(any(Turma.class));

		ModelAndView mv = turmaController.AdicionarTurma(turma, 1L, "CODE123", session);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testListarTurmasException() {
		when(turmaImplService.getDiciplina_Turma()).thenThrow(new RuntimeException("Fetch error"));

		ModelAndView mv = turmaController.ListarTurma();

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testListarTurmaAlunosException() {
		when(turmaImplService.getDiciplina_TurmaID(1L)).thenThrow(new RuntimeException("Fetch error"));

		ModelAndView mv = turmaController.ListarTurmaAlunos(1L);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testPesquisaporTurmasException() {
		when(turmaImplService.pesquisarTurmas("test")).thenThrow(new RuntimeException("Search error"));

		ModelAndView mv = turmaController.pesquisaporTurmas("test");

		assertEquals("redirect:/error", mv.getViewName());
	}
}
