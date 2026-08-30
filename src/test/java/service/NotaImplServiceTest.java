package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.TooDevs.br.ApiParaBoletimEscolarApplication;

import Dto.NotaDto;
import domain.Aluno;
import domain.Diciplina;
import domain.Nota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest(classes = ApiParaBoletimEscolarApplication.class)
@Transactional
public class NotaImplServiceTest {

    @Autowired
    private NotaImplService notaImplService;

    @Autowired
    private AlunoImplService alunoImplService;

    @Autowired
    private DiciplinaImplService diciplinaImplService;

    @PersistenceContext
    private EntityManager entityManager;

    private Long alunoId;

    @BeforeEach
    public void setUp() {
        Aluno aluno = new Aluno();
        aluno.setNome("Test Aluno");
        aluno.setMatricula("123456789012");
        aluno.setSenha("secret1");
        aluno.setDataNascimento(new Date());
        alunoImplService.Salvar(aluno);
        alunoId = aluno.getId();

        for (int i = 0; i < 5; i++) {
            Diciplina diciplina = new Diciplina();
            diciplina.setNome("Disciplina " + i);
            diciplinaImplService.Salvar(diciplina);

            for (int j = 0; j < 20; j++) {
                Nota nota = new Nota();
                nota.setAluno_id(aluno);
                nota.setDiciplina_id(diciplina);
                nota.setMedia(8.5);
                nota.setNota_trabalho(9.0);
                nota.setNota_Teste(8.0);
                nota.setNota_prova(8.5);
                notaImplService.Salvar(nota);
            }
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testListarImpressaoNotaAlunoCorrectnessAndPerformance() {
        // Warmup
        for (int i = 0; i < 10; i++) {
            List<NotaDto> list = notaImplService.ListarImpressaoNotaAluno(alunoId);
            assertEquals(100, list.size());
        }

        long startTime = System.currentTimeMillis();
        int iterations = 200;
        for (int i = 0; i < iterations; i++) {
            entityManager.clear();
            List<NotaDto> result = notaImplService.ListarImpressaoNotaAluno(alunoId);
            assertEquals(100, result.size());
            assertNotNull(result.get(0).getNome_aluno());
            assertNotNull(result.get(0).getDiciplina_nome());
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("BASELINE EXECUTION TIME for " + iterations + " iterations: " + duration + " ms");
    }
}
