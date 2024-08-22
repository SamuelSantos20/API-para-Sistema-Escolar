package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import domain.Turma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class TurmaImpl extends AbstractDao<Turma, Long> implements TurmaDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Turma> findTurmasByAlunoId(Long alunoId) {
		String jpql = "SELECT t FROM Turma t " + "JOIN t.alunos a " + "JOIN t.Diciplinas d " + "JOIN d.professores p "
				+ "WHERE a.id = :alunoId";
		TypedQuery<Turma> query = entityManager.createQuery(jpql, Turma.class);
		query.setParameter("alunoId", alunoId);
		return query.getResultList();
	}

	public List<Turma> ListarPorID(Long id) {
		try {

			String jpql = "select t from Turma t where t.id = :id";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("id", id);
			return query.getResultList();

		} catch (Exception e) {

			System.out.println(e);
             return new ArrayList<>();
		}

	}
	
	
	public List<Turma> PesquisarTurmas(String texto){
		try {
			String jpql = "select t from Turma t where t.nome like :texto or t.codigo_turma like :texto ";
			
			Query query = entityManager.createQuery(jpql);
			query.setParameter("texto", "%"+texto+"%");
			return query.getResultList();
			
		} catch (Exception e) {
		return new ArrayList<>();
		}
		
		
	}
	
	
	
	

}
