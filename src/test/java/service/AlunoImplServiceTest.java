package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Dto.AlunoDto;
import dao.AlunoDao;
import domain.Aluno;
import domain.Turma;

@ExtendWith(MockitoExtension.class)
class AlunoImplServiceTest {

	@Mock
	private AlunoDao alunoDao;

	@InjectMocks
	private AlunoImplService alunoImplService;

	@Test
	void listaCompleta_ShouldReturnMappedAlunoDtoList_WhenAlunosExist() {
		Turma turma = new Turma();
		turma.setId(1L);

		Date dataNascimento = new Date();

		Aluno aluno1 = new Aluno();
		aluno1.setId(10L);
		aluno1.setNome("João");
		aluno1.setMatricula("2023001");
		aluno1.setDataNascimento(dataNascimento);
		aluno1.setSenha("senha123");
		aluno1.setTurmaId(turma);

		Aluno aluno2 = new Aluno();
		aluno2.setId(11L);
		aluno2.setNome("Maria");
		aluno2.setMatricula("2023002");
		aluno2.setDataNascimento(dataNascimento);
		aluno2.setSenha("senha456");
		aluno2.setTurmaId(turma);

		when(alunoDao.findAll()).thenReturn(List.of(aluno1, aluno2));

		List<AlunoDto> result = alunoImplService.ListaCompleta();

		assertNotNull(result);
		assertEquals(2, result.size());

		AlunoDto dto1 = result.stream()
				.filter(d -> "2023001".equals(d.getMatricula()))
				.findFirst()
				.orElse(null);
		assertNotNull(dto1);
		assertEquals("João", dto1.getNome());
		assertEquals("2023001", dto1.getMatricula());
		assertEquals(dataNascimento, dto1.getData_nascimento());
		assertEquals("senha123", dto1.getSenha());
		assertEquals(turma, dto1.getTurma());

		AlunoDto dto2 = result.stream()
				.filter(d -> "2023002".equals(d.getMatricula()))
				.findFirst()
				.orElse(null);
		assertNotNull(dto2);
		assertEquals("Maria", dto2.getNome());
		assertEquals("2023002", dto2.getMatricula());

		verify(alunoDao).findAll();
	}

	@Test
	void listaCompleta_ShouldReturnEmptyList_WhenNoAlunosExist() {
		when(alunoDao.findAll()).thenReturn(Collections.emptyList());

		List<AlunoDto> result = alunoImplService.ListaCompleta();

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(alunoDao).findAll();
	}

	@Test
	void listaCompleta_ShouldHandleNullFieldsInAluno() {
		Aluno alunoWithNulls = new Aluno();

		when(alunoDao.findAll()).thenReturn(List.of(alunoWithNulls));

		List<AlunoDto> result = alunoImplService.ListaCompleta();

		assertNotNull(result);
		assertEquals(1, result.size());

		AlunoDto dto = result.get(0);
		assertNull(dto.getNome());
		assertNull(dto.getMatricula());
		assertNull(dto.getData_nascimento());
		assertNull(dto.getSenha());
		assertNull(dto.getTurma());

		verify(alunoDao).findAll();
	}
}
