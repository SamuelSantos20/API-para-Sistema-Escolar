package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import domain.Diciplina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class DiciplinaImpl extends AbstractDao<Diciplina, Long> implements DiciplinaDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Diciplina> BuscarPordiciplina(String texto) {

		String jpql = "select d from Diciplina d where d.nome like :nome";

		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", "%" + texto + "%");

		try {

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}

	}

	public List<Diciplina> PesquisarDiciplinaId(Long id) {

		try {
			String jpql = "select d from Diciplina d where d.id = :id";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("id", id);
			return query.getResultList();
		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}

	}

}
