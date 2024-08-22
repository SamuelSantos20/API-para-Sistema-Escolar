package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public abstract class AbstractDao<T, PK extends Serializable> {

    // Obtém a classe da entidade T através de reflexão
    @SuppressWarnings("unchecked")
    private final Class<T> entityClass =
            (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    // Injeção de dependência do EntityManager pelo contexto de persistência
    @PersistenceContext
    private EntityManager entityManager;

    // Método para obter o EntityManager protegido para subclasses
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    // Método para persistir uma entidade T no banco de dados
    public void save(T entity) {
        entityManager.persist(entity);
    }

    // Método para atualizar uma entidade T no banco de dados
    public void update(T entity) {
        entityManager.merge(entity);
    }

    // Método para excluir uma entidade T do banco de dados por sua chave primária (PK)
    public void delete(PK id) {
        T entityToRemove = entityManager.getReference(entityClass, id);
        entityManager.remove(entityToRemove);
    }

    // Método para encontrar uma entidade T pelo seu ID (PK)
    public T findById(PK id) {
        return entityManager.find(entityClass, id);
    }

    // Método para encontrar todas as entidades T no banco de dados
    public List<T> findAll() {
        return entityManager
                .createQuery("from " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }
    
   

    // Método genérico para criar consultas JPQL customizadas
    protected List<T> createQuery(String jpql, Object... params) {
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }
}