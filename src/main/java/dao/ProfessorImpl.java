package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class ProfessorImpl extends AbstractDao<Professor, Long>  implements ProfessorDao{

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PersistenceContext
	private EntityManager entityManager;
	

	public Optional<Professor> findByMatriculaAndSenha(String matricula, String senha) {
	    String jpql = "select p from Professor p where p.matricula = :matricula";
	    Query query = entityManager.createQuery(jpql);
	    query.setParameter("matricula", matricula);
	    List<Professor> result = query.getResultList();
	    
	    if (result.isEmpty()) {
	        return null;
	    } else {
	        Professor professor = result.get(0);
	        if (professor.getSenha() != null && passwordEncoder.matches(senha, professor.getSenha())) {
	            return Optional.of(professor);
	        } else {
	            return null;
	        }
	    }
	    
	}

}
