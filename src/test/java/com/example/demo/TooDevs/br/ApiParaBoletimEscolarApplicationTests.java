package com.example.demo.TooDevs.br;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dao.AdministradorDao;
import dao.Administradorimpl;
import dao.AlunoDao;
import dao.AlunoImpl;
import dao.ProfessorDao;
import dao.ProfessorImpl;
import domain.Administrador;
import domain.Aluno;
import domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import service.AdministradorImplService;
import service.AlunoImplService;
import service.ProfessorImplService;

class ApiParaBoletimEscolarApplicationTests {

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	void testAdministradorServicePasswordHashingOnSave() throws Exception {
		AdministradorDao dao = mock(AdministradorDao.class);
		AdministradorImplService service = new AdministradorImplService();

		Field daoField = AdministradorImplService.class.getDeclaredField("administradorDao");
		daoField.setAccessible(true);
		daoField.set(service, dao);

		Administrador adm = new Administrador();
		adm.setSenha("plainSenha123");

		service.Salvar(adm);

		assertTrue(passwordEncoder.matches("plainSenha123", adm.getSenha()));
		verify(dao).save(adm);
	}

	@Test
	void testAlunoServicePasswordHashingOnSave() throws Exception {
		AlunoDao dao = mock(AlunoDao.class);
		AlunoImplService service = new AlunoImplService();

		Field daoField = AlunoImplService.class.getDeclaredField("alunoDao");
		daoField.setAccessible(true);
		daoField.set(service, dao);

		Aluno aluno = new Aluno();
		aluno.setSenha("secretAlunoPass");

		service.Salvar(aluno);

		assertTrue(passwordEncoder.matches("secretAlunoPass", aluno.getSenha()));
		verify(dao).save(aluno);
	}

	@Test
	void testProfessorServicePasswordHashingOnSave() throws Exception {
		ProfessorDao dao = mock(ProfessorDao.class);
		ProfessorImplService service = new ProfessorImplService();

		Field daoField = ProfessorImplService.class.getDeclaredField("professorDao");
		daoField.setAccessible(true);
		daoField.set(service, dao);

		Professor prof = new Professor();
		prof.setSenha("profPass321");

		service.Salvar(prof);

		assertTrue(passwordEncoder.matches("profPass321", prof.getSenha()));
		verify(dao).save(prof);
	}

	@Test
	void testAdministradorDaoFindByMatriculaAndSenha() throws Exception {
		Administradorimpl dao = new Administradorimpl();
		EntityManager em = mock(EntityManager.class);
		Query query = mock(Query.class);

		Field emField = Administradorimpl.class.getDeclaredField("entityManager");
		emField.setAccessible(true);
		emField.set(dao, em);

		when(em.createQuery(anyString())).thenReturn(query);

		Administrador adm = new Administrador();
		adm.setMatricula("ADM001");
		adm.setSenha(passwordEncoder.encode("correctPassword"));

		when(query.getResultList()).thenReturn(Collections.singletonList(adm));

		Optional<Administrador> success = dao.findByMatriculaAndSenha("ADM001", "correctPassword");
		assertNotNull(success);
		assertTrue(success.isPresent());

		Optional<Administrador> fail = dao.findByMatriculaAndSenha("ADM001", "wrongPassword");
		assertNull(fail);
	}

	@Test
	void testAlunoDaoFindByMatriculaAndSenha() throws Exception {
		AlunoImpl dao = new AlunoImpl();
		EntityManager em = mock(EntityManager.class);
		Query query = mock(Query.class);

		Field emField = AlunoImpl.class.getDeclaredField("entityManager");
		emField.setAccessible(true);
		emField.set(dao, em);

		when(em.createQuery(anyString())).thenReturn(query);

		Aluno aluno = new Aluno();
		aluno.setMatricula("ALU001");
		aluno.setSenha(passwordEncoder.encode("alunoPass"));

		when(query.getResultList()).thenReturn(Collections.singletonList(aluno));

		Optional<Aluno> success = dao.findByMatriculaAndSenha("ALU001", "alunoPass");
		assertNotNull(success);
		assertTrue(success.isPresent());

		Optional<Aluno> fail = dao.findByMatriculaAndSenha("ALU001", "wrongPass");
		assertNull(fail);
	}

	@Test
	void testProfessorDaoFindByMatriculaAndSenha() throws Exception {
		ProfessorImpl dao = new ProfessorImpl();
		EntityManager em = mock(EntityManager.class);
		Query query = mock(Query.class);

		Field emField = ProfessorImpl.class.getDeclaredField("entityManager");
		emField.setAccessible(true);
		emField.set(dao, em);

		when(em.createQuery(anyString())).thenReturn(query);

		Professor prof = new Professor();
		prof.setMatricula("PROF001");
		prof.setSenha(passwordEncoder.encode("profPass"));

		when(query.getResultList()).thenReturn(Collections.singletonList(prof));

		Optional<Professor> success = dao.findByMatriculaAndSenha("PROF001", "profPass");
		assertNotNull(success);
		assertTrue(success.isPresent());

		Optional<Professor> fail = dao.findByMatriculaAndSenha("PROF001", "wrongPass");
		assertNull(fail);
	}
}
