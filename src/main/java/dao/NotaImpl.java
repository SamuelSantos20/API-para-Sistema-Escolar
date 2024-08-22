package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import domain.Nota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class NotaImpl extends AbstractDao<Nota,Long > implements NotaDao{

	
	@PersistenceContext 
	private EntityManager entityManager;
	
	public List<Nota> NotasAluno(Long id) {
	    try {
	        // Corrigido para usar a entidade Nota diretamente
	        String jpql = "SELECT n FROM Nota n WHERE n.aluno_id.id = :id";
	        Query query = entityManager.createQuery(jpql);
	        query.setParameter("id", id);

	        return query.getResultList();
	        
	    } catch (Exception e) {
	        // Retornando uma lista vazia em vez de null
	        return new ArrayList<>();
	    }
	}
	
	public Optional<Nota> NotasAlunoEditar(Long id) {
		try {
			// Corrigido para usar a entidade Nota diretamente
			String jpql = "SELECT n FROM Nota n WHERE n.aluno_id.id = :id";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("id", id);
			List<Nota> result = query.getResultList();
			return Optional.of(result.get(0));
			
		} catch (Exception e) {
			
			return Optional.empty();
		}
	}
	
     public List<Nota> BuscarNomeAluno(String texto){
    	 
    	 try {
			String jpql = "SELECT n FROM Nota n WHERE n.aluno_id.nome like :texto";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("texto", "%"+ texto+"%");
			return query.getResultList();
			
		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
    	 
     }

}
