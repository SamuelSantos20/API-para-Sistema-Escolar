package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import domain.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class AlunoImpl extends AbstractDao<Aluno, Long> implements AlunoDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Optional<Aluno> findByMatriculaAndSenha(String matricula, String senha) {
		String jpql = "select m from Aluno m where m.matricula = :matricula and m.senha = :senha";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("matricula", matricula);
		query.setParameter("senha", senha);
		List<Aluno> result = query.getResultList();

		if (result.isEmpty()) {
			return null;
		} else {
			return Optional.of(result.get(0));
		}
	}

	public List<Aluno> ListarAluno(Long id) {
		try {

			String jpql = "select a from Aluno a where a.id = :id";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("id", id);
			
			return query.getResultList();

		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	public List<Aluno> BuscarporAlunoOrMatricula(String texto) {

		String jpql = "select l from Aluno l where l.nome like :texto or l.matricula  like :texto";

		Query query = entityManager.createQuery(jpql);

		query.setParameter("texto", "%" + texto + "%");
		query.setParameter("texto", "%" + texto + "%");

		List<Aluno> alunos = query.getResultList();

		try {

			return alunos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
