package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class Administradorimpl extends AbstractDao<Administrador, Long> implements AdministradorDao{

	
	
	@PersistenceContext
	private EntityManager entityManager;
	@Transactional(readOnly = true)
	public Optional<Administrador> findByMatriculaAndSenha(String matricula, String senha) {
	    String jpql = "select m from Administrador m where m.matricula = :matricula and m.senha = :senha";
	    Query query = entityManager.createQuery(jpql);
	    query.setParameter("matricula", matricula);
	    query.setParameter("senha", senha);
	    List<Administrador> result = query.getResultList();
	    
	    if (result.isEmpty()) {
	        return null;
	    } else {
	        return Optional.of(result.get(0));
	    }
	}
}
