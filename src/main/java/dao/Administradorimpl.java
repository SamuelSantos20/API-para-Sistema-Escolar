package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class Administradorimpl extends AbstractDao<Administrador, Long> implements AdministradorDao{

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public Optional<Administrador> findByMatriculaAndSenha(String matricula, String senha) {
	    String jpql = "select m from Administrador m where m.matricula = :matricula";
	    Query query = entityManager.createQuery(jpql);
	    query.setParameter("matricula", matricula);
	    List<Administrador> result = query.getResultList();
	    
	    if (result.isEmpty()) {
	        return null;
	    } else {
	        Administrador adm = result.get(0);
	        if (adm.getSenha() != null && passwordEncoder.matches(senha, adm.getSenha())) {
	            return Optional.of(adm);
	        } else {
	            return null;
	        }
	    }
	}
}
