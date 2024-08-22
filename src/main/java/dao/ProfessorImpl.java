package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class ProfessorImpl extends AbstractDao<Professor, Long>  implements ProfessorDao{

	@PersistenceContext
	private EntityManager entityManager;
	

	public Optional<Professor> findByMatriculaAndSenha(String matricula, String senha) {
	    String jpql = "select p from Professor p where p.matricula = :matricula and p.senha = :senha";
	    Query query = entityManager.createQuery(jpql);
	    query.setParameter("matricula", matricula);
	    query.setParameter("senha", senha);
	    List<Professor> result = query.getResultList();
	    
	    if (result.isEmpty()) {
	        return null;
	    } else {
	        return Optional.of(result.get(0));
	    }
	    
	}

}
