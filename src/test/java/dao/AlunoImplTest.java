package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import domain.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

class AlunoImplTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private AlunoImpl alunoImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testListarAlunoSuccess() {
		Query query = mock(Query.class);
		Aluno aluno = new Aluno();
		aluno.setId(1L);

		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of(aluno));

		List<Aluno> result = alunoImpl.ListarAluno(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void testListarAlunoExceptionHandling() {
		when(entityManager.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

		List<Aluno> result = alunoImpl.ListarAluno(1L);

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void testBuscarporAlunoOrMatriculaSuccess() {
		Query query = mock(Query.class);
		Aluno aluno = new Aluno();
		aluno.setNome("João");

		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of(aluno));

		List<Aluno> result = alunoImpl.BuscarporAlunoOrMatricula("João");

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void testBuscarporAlunoOrMatriculaExceptionHandling() {
		when(entityManager.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

		List<Aluno> result = alunoImpl.BuscarporAlunoOrMatricula("João");

		// When entityManager throws exception before getResultList in BuscarporAlunoOrMatricula, catch handles it and returns null
		assertEquals(null, result);
	}
}
