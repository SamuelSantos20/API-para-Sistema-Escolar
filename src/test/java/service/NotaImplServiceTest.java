package service;

import Dto.NotaDto;
import dao.NotaDao;
import domain.Aluno;
import domain.Diciplina;
import domain.Nota;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotaImplServiceTest {

    @Mock
    private NotaDao notaDao;

    @InjectMocks
    private NotaImplService notaImplService;

    @Test
    void testListarRelatorioUsesOptimizedQuery() {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");

        Diciplina diciplina = new Diciplina();
        diciplina.setNome("Matemática");

        Nota nota = new Nota();
        nota.setAluno_id(aluno);
        nota.setDiciplina_id(diciplina);
        nota.setMedia(8.5);
        nota.setNota_trabalho(9.0);
        nota.setNota_Teste(8.0);
        nota.setNota_prova(8.5);

        when(notaDao.findAllWithAlunoAndDiciplina()).thenReturn(List.of(nota));

        List<NotaDto> result = notaImplService.ListarRelatorio();

        assertNotNull(result);
        assertEquals(1, result.size());
        NotaDto dto = result.get(0);
        assertEquals("João Silva", dto.getNome_aluno());
        assertEquals("Matemática", dto.getDiciplina_nome());
        assertEquals(8.5, dto.getMedia());
        assertEquals(9.0, dto.getNota_trabalho());
        assertEquals(8.0, dto.getNota_teste());
        assertEquals(8.5, dto.getNota_prova());

        verify(notaDao).findAllWithAlunoAndDiciplina();
    }
}
