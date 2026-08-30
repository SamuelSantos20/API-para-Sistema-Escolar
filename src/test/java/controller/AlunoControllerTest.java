package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import Dto.AlunoDto;
import Dto.NotaDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AlunoImplService;
import service.NotaImplService;
import util.GeradordeRelatorio;

@ExtendWith(MockitoExtension.class)
class AlunoControllerTest {

	@Mock
	private AlunoImplService alunoImplService;

	@Mock
	private NotaImplService notaImplService;

	@Mock
	private GeradordeRelatorio geradordeRelatorio;

	@Mock
	private HttpSession session;

	@Mock
	private HttpServletResponse response;

	@InjectMocks
	private AlunoController alunoController;

	@Test
	void getMethodName_Success() {
		when(session.getAttribute("aluno")).thenReturn(1L);
		List<AlunoDto> alunos = Collections.emptyList();
		when(alunoImplService.ListaUnica(1L)).thenReturn(alunos);

		ModelAndView mv = alunoController.getMethodName(session);

		assertEquals("Aluno/aluno.html", mv.getViewName());
		assertEquals(alunos, mv.getModel().get("Alunos"));
	}

	@Test
	void getMethodName_Exception_RedirectsToError() {
		when(session.getAttribute("aluno")).thenReturn(1L);
		when(alunoImplService.ListaUnica(1L)).thenThrow(new RuntimeException("Database error"));

		ModelAndView mv = alunoController.getMethodName(session);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void listarNotasAluno_Success() {
		when(session.getAttribute("aluno")).thenReturn(1L);
		List<NotaDto> notas = Collections.emptyList();
		when(notaImplService.ListarImpressaoNotaAluno(1L)).thenReturn(notas);

		ModelAndView mv = alunoController.ListarNotasAluno(session);

		assertEquals("Notas/notas.html", mv.getViewName());
		assertEquals(notas, mv.getModel().get("Notas"));
	}

	@Test
	void listarNotasAluno_Exception_RedirectsToError() {
		when(session.getAttribute("aluno")).thenReturn(1L);
		when(notaImplService.ListarImpressaoNotaAluno(1L)).thenThrow(new RuntimeException("Error"));

		ModelAndView mv = alunoController.ListarNotasAluno(session);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void listarTodosAlunos_Success() {
		List<AlunoDto> alunos = Collections.emptyList();
		when(alunoImplService.ListaCompleta()).thenReturn(alunos);

		ModelAndView mv = alunoController.ListarTodosAlunos(session);

		assertEquals("Aluno/alunos.html", mv.getViewName());
		assertEquals(alunos, mv.getModel().get("Alunos"));
	}

	@Test
	void listarTodosAlunos_Exception_RedirectsToError() {
		when(alunoImplService.ListaCompleta()).thenThrow(new RuntimeException("Error"));

		ModelAndView mv = alunoController.ListarTodosAlunos(session);

		assertEquals("redirect:/error", mv.getViewName());
	}

	@Test
	void impressaodeNota_Success() {
		when(session.getAttribute("aluno")).thenReturn(1L);
		List<NotaDto> notas = Collections.emptyList();
		when(notaImplService.ListarImpressaoNotaAluno(1L)).thenReturn(notas);

		alunoController.ImpressaodeNota(session, response);

		verify(geradordeRelatorio).gerarRelatoriocontato(response, notas);
	}

	@Test
	void pesquisarAluno_Success() {
		List<AlunoDto> alunos = Collections.emptyList();
		when(alunoImplService.PesquisaDto("João")).thenReturn(alunos);

		ModelAndView mv = alunoController.PesquisarAluno("João");

		assertEquals("Aluno/alunos.html", mv.getViewName());
		assertEquals(alunos, mv.getModel().get("Alunos"));
	}

	@Test
	void pesquisarAluno_Exception_RedirectsToError() {
		when(alunoImplService.PesquisaDto("João")).thenThrow(new RuntimeException("Error"));

		ModelAndView mv = alunoController.PesquisarAluno("João");

		assertEquals("redirect:/error", mv.getViewName());
	}
}
