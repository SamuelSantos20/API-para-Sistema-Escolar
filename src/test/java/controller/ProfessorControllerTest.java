package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import Dto.NotaDto;
import service.AlunoImplService;
import service.DiciplinaImplService;
import service.NotaImplService;
import service.ProfessorImplService;
import util.GeradordeRelatorio;

@ExtendWith(MockitoExtension.class)
public class ProfessorControllerTest {

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

    @BeforeEach
    void setUp() {
    }

    @Test
    void testVerifinotasAluno_Success() {
        Long id = 1L;
        List<NotaDto> list = Collections.emptyList();
        when(notaImplService.ListarImpressaoNotaAluno(id)).thenReturn(list);

        ModelAndView mv = professorController.verifinotasAluno(id);

        assertEquals("Professor/verificarnotas-alunos.html", mv.getViewName());
        assertEquals(list, mv.getModel().get("Notas"));
    }

    @Test
    void testVerifinotasAluno_Exception() {
        Long id = 1L;
        when(notaImplService.ListarImpressaoNotaAluno(id)).thenThrow(new RuntimeException("Database error"));

        ModelAndView mv = professorController.verifinotasAluno(id);

        assertEquals("redirect:/error", mv.getViewName());
    }
}
