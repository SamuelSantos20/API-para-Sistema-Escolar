package com.example.demo.TooDevs.br;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import Dto.NotaDto;
import dao.AlunoDao;
import dao.DiciplinaDao;
import dao.NotaDao;
import domain.Aluno;
import domain.Diciplina;
import domain.Nota;
import service.NotaImplService;

@SpringBootTest
@Transactional
public class NotaServiceTest {

    @Autowired
    private NotaImplService notaImplService;

    @Autowired
    private NotaDao notaDao;

    @Autowired
    private AlunoDao alunoDao;

    @Autowired
    private DiciplinaDao diciplinaDao;

    @Test
    public void testListarImpressaoNotaAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Test Student");
        aluno.setMatricula("123456789012");
        aluno.setSenha("pass123");
        alunoDao.save(aluno);

        Diciplina diciplina = new Diciplina();
        diciplina.setNome("Math");
        diciplinaDao.save(diciplina);

        for (int i = 0; i < 50; i++) {
            Nota nota = new Nota();
            nota.setAluno_id(aluno);
            nota.setDiciplina_id(diciplina);
            nota.setMedia(9.0);
            nota.setNota_trabalho(10.0);
            nota.setNota_Teste(8.0);
            nota.setNota_prova(9.0);
            notaDao.save(nota);
        }

        long start = System.currentTimeMillis();
        List<NotaDto> list = notaImplService.ListarImpressaoNotaAluno(aluno.getId());
        long duration = System.currentTimeMillis() - start;

        Assertions.assertEquals(50, list.size());
        Assertions.assertEquals("Test Student", list.get(0).getNome_aluno());
        Assertions.assertEquals("Math", list.get(0).getDiciplina_nome());
        System.out.println("Execution time for ListarImpressaoNotaAluno: " + duration + " ms");
    }
}
