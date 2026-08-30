package service;

import Dto.NotaDto;
import domain.Aluno;
import domain.Diciplina;
import domain.Nota;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest(classes = com.example.demo.TooDevs.br.ApiParaBoletimEscolarApplication.class)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
public class NotaImplServiceTest {

    @Autowired
    private NotaImplService notaImplService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testListarImpressaoNotaAlunoPerformance() {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setSenha("senha123");
        entityManager.persist(aluno);

        int count = 500;
        for (int i = 0; i < count; i++) {
            Diciplina diciplina = new Diciplina();
            diciplina.setNome("Disciplina " + i);
            entityManager.persist(diciplina);

            Nota nota = new Nota();
            nota.setAluno_id(aluno);
            nota.setDiciplina_id(diciplina);
            nota.setMedia(8.5);
            nota.setNota_trabalho(8.0);
            nota.setNota_Teste(8.5);
            nota.setNota_prova(9.0);
            entityManager.persist(nota);
        }

        entityManager.flush();
        entityManager.clear();

        long startTime = System.nanoTime();
        List<NotaDto> dtos = notaImplService.ListarImpressaoNotaAluno(aluno.getId());
        long durationMs = (System.nanoTime() - startTime) / 1_000_000;

        Assertions.assertEquals(count, dtos.size());
        Assertions.assertEquals("João Silva", dtos.get(0).getNome_aluno());
        System.out.println("Execution time for ListarImpressaoNotaAluno with " + count + " records: " + durationMs + " ms");
    }
}
