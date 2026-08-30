package controller;

import domain.Abrangencia;
import domain.Aluno;
import domain.Diciplina;
import domain.Nota;
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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
	void testRequestAddNota_Authorized() {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("abrangencia", Abrangencia.PROFESSOR);

		when(alunoImplService.ListarTodos()).thenReturn(Collections.emptyList());
		when(diciplinaImplService.ListarTodas()).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.requestAddNota(session);

		assertEquals("Notas/adicionar-notas.html", mv.getViewName());
		assertNotNull(mv.getModel().get("Alunos"));
		assertNotNull(mv.getModel().get("Diciplinas"));
	}

	@Test
	void testRequestAddNota_Unauthorized() {
		MockHttpSession session = new MockHttpSession();

		ModelAndView mv = professorController.requestAddNota(session);

		assertEquals("index.html", mv.getViewName());
		assertNotNull(mv.getModel().get("errorMessage"));
	}

	@Test
	void testBuscarAluno_Success() {
		MockHttpSession session = new MockHttpSession();
		when(alunoImplService.PesquisarAluno("João")).thenReturn(Collections.emptyList());
		when(diciplinaImplService.ListarTodas()).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.postMethodName("João", session);

		assertEquals("Notas/adicionar-notas.html", mv.getViewName());
	}

	@Test
	void testPesquisarMateria_Success() {
		MockHttpSession session = new MockHttpSession();
		when(diciplinaImplService.PesquisarDiciplina("Math")).thenReturn(Collections.emptyList());
		when(alunoImplService.ListarTodos()).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.PesquisarMateria("Math", session);

		assertEquals("Notas/adicionar-notas.html", mv.getViewName());
	}

	@Test
	void testAdicionarNota_Success() {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("abrangencia", Abrangencia.PROFESSOR);
		Nota nota = new Nota();
		nota.setNota_trabalho(8.0);
		nota.setNota_Teste(7.0);
		nota.setNota_prova(9.0);

		when(alunoImplService.ListarTodos()).thenReturn(Collections.emptyList());
		when(diciplinaImplService.ListarTodas()).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.AdicionarNota(nota, session, 1L, 2L);

		assertEquals("Notas/adicionar-notas.html", mv.getViewName());
		assertNotNull(mv.getModel().get("successMessage"));
	}

	@Test
	void testRelatorio_Success() {
		when(notaImplService.ListarRelatorio()).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.Relatorio();

		assertEquals("Relatorios/relatorios.html", mv.getViewName());
	}

	@Test
	void testPesquisarAluno_Success() {
		MockHttpSession session = new MockHttpSession();
		when(notaImplService.BuscarPorAluno("Maria")).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.PesquisarAluno("Maria", session);

		assertEquals("Relatorios/relatoriopesquisa.html", mv.getViewName());
		assertEquals("Maria", session.getAttribute("texto"));
	}

	@Test
	void testRequetsProfessore_ADM() {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("abrangencia", Abrangencia.ADM);

		when(professorImplService.RequestListarProfessores()).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.RequetsProfessore(session);

		assertEquals("Professor/professores.html", mv.getViewName());
	}

	@Test
	void testPreEditarNota_Success() {
		MockHttpSession session = new MockHttpSession();
		Nota nota = new Nota();
		when(notaImplService.ListarIdAluno(1L)).thenReturn(Optional.of(nota));
		when(alunoImplService.ListarOneAluno(1L)).thenReturn(Collections.singletonList(new Aluno()));
		when(diciplinaImplService.PesquisarDiciplinasId(2L)).thenReturn(Collections.singletonList(new Diciplina()));

		ModelAndView mv = professorController.PreEditarNota(1L, session, 2L);

		assertEquals("Notas/adicionar-notas.html", mv.getViewName());
		assertEquals(1L, session.getAttribute("id_aluno_nota"));
		assertEquals(2L, session.getAttribute("id_diciplina_nota"));
	}

	@Test
	void testPreEditarNota_Exception_RedirectsToErrorAndLogs() {
		MockHttpSession session = new MockHttpSession();
		when(notaImplService.ListarIdAluno(anyLong())).thenThrow(new RuntimeException("Database error"));

		ModelAndView mv = professorController.PreEditarNota(1L, session, 2L);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void testEditarNota_Success() {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("id_aluno_nota", 1L);
		session.setAttribute("id_diciplina_nota", 2L);
		Nota nota = new Nota();
		nota.setNota_trabalho(10.0);
		nota.setNota_Teste(10.0);
		nota.setNota_prova(10.0);

		when(notaImplService.ListarIdAluno(1L)).thenReturn(Optional.of(nota));
		when(alunoImplService.ListarOneAluno(1L)).thenReturn(Collections.singletonList(new Aluno()));
		when(diciplinaImplService.PesquisarDiciplinasId(2L)).thenReturn(Collections.singletonList(new Diciplina()));

		ModelAndView mv = professorController.EditarNota(nota, session, 2L, 1L);

		assertEquals("Notas/adicionar-notas.html", mv.getViewName());
	}

	@Test
	void testVerifinotasAluno_Success() {
		when(notaImplService.ListarImpressaoNotaAluno(1L)).thenReturn(Collections.emptyList());

		ModelAndView mv = professorController.verifinotasAluno(1L);

		assertEquals("Professor/verificarnotas-alunos.html", mv.getViewName());
	}

	@Test
	void testVerifinotasAluno_Exception_RedirectsToErrorAndLogs() {
		when(notaImplService.ListarImpressaoNotaAluno(anyLong())).thenThrow(new RuntimeException("Simulated exception"));

		ModelAndView mv = professorController.verifinotasAluno(1L);

		assertEquals("redirect:/error", mv.getViewName());
	}
}
