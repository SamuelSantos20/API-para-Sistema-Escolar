package service;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import Dto.AlunoDto;
import domain.Aluno;
import domain.Turma;

@SpringBootTest(classes = com.example.demo.TooDevs.br.ApiParaBoletimEscolarApplication.class)
public class AlunoImplServiceBenchmarkTest {

    @Autowired
    private AlunoImplService alunoImplService;

    @Autowired
    private TurmaImplService turmaImplService;

    @BeforeEach
    public void setUp() {
        if (alunoImplService.ListarTodos().isEmpty()) {
            Turma turma1 = new Turma();
            turma1.setNome("Turma A");
            turma1.setCodigo_turma("TURMA_A");
            turma1.setDescricao("Desc A");
            turma1.setData_inicio(new Date());
            turma1.setData_fim(new Date());
            turmaImplService.Salvar(turma1);

            Turma turma2 = new Turma();
            turma2.setNome("Turma B");
            turma2.setCodigo_turma("TURMA_B");
            turma2.setDescricao("Desc B");
            turma2.setData_inicio(new Date());
            turma2.setData_fim(new Date());
            turmaImplService.Salvar(turma2);

            for (int i = 0; i < 500; i++) {
                Aluno aluno = new Aluno();
                aluno.setNome("Aluno " + i);
                aluno.setMatricula("MAT" + i);
                aluno.setSenha("PASS" + i);
                aluno.setDataNascimento(new Date());
                aluno.setTurmaId(i % 2 == 0 ? turma1 : turma2);
                alunoImplService.Salvar(aluno);
            }
        }
    }

    @Test
    public void benchmarkListaCompleta() {
        // Warm up
        for (int i = 0; i < 5; i++) {
            List<AlunoDto> list = alunoImplService.ListaCompleta();
            org.junit.jupiter.api.Assertions.assertEquals(500, list.size());
        }

        long startTime = System.nanoTime();
        int iterations = 20;
        for (int i = 0; i < iterations; i++) {
            List<AlunoDto> list = alunoImplService.ListaCompleta();
            org.junit.jupiter.api.Assertions.assertEquals(500, list.size());
        }
        long endTime = System.nanoTime();

        double averageMs = (endTime - startTime) / 1e6 / iterations;
        System.out.println("=== BENCHMARK RESULT ===");
        System.out.println("Average execution time for ListaCompleta(): " + averageMs + " ms");
        System.out.println("========================");
    }
}
