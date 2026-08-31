package com.example.demo.TooDevs.br;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import Dto.ProfessorDto;
import dao.DiciplinaDao;
import dao.ProfessorDao;
import domain.Diciplina;
import domain.Professor;
import jakarta.persistence.EntityManager;
import service.ProfessorImplService;

@SpringBootTest
@ActiveProfiles("test")
class ProfessorServiceTest {

    @Autowired
    private ProfessorImplService professorService;

    @Autowired
    private ProfessorDao professorDao;

    @Autowired
    private DiciplinaDao diciplinaDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void benchmarkRequestListarProfessores() {
        int count = 50;
        for (int i = 0; i < count; i++) {
            Professor prof = new Professor();
            prof.setNome("Prof " + i);
            prof.setMatricula("MAT" + String.format("%05d", i));
            prof.setSenha("pass" + i);
            prof.setTelefone("12345" + String.format("%05d", i));
            prof.setEmail("prof" + i + "@test.com");
            professorDao.save(prof);

            Diciplina disc = new Diciplina();
            disc.setNome("Disciplina " + i);
            disc.getProfessores().add(prof);
            diciplinaDao.save(disc);

            prof.getDiciplinas().add(disc);
        }

        entityManager.flush();
        entityManager.clear();

        long startTime = System.nanoTime();
        List<ProfessorDto> dtos = professorService.RequestListarProfessores();
        for (ProfessorDto dto : dtos) {
            assertEquals(1, dto.getDiciplina().size());
        }
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1000000;
        System.out.println("Benchmark RequestListarProfessores executed in: " + durationMs + " ms for " + dtos.size() + " professors.");
    }
}
