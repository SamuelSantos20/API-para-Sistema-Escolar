package domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Classe base abstrata para entidades com um identificador genérico.
 *
 * @param <ID> Tipo do identificador da entidade, que deve ser serializável.
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    /**
     * Obtém o identificador da entidade.
     * 
     * @return O identificador da entidade.
     */
    public ID getId() {
        return id;
    }

    /**
     * Define o identificador da entidade.
     * 
     * @param id O identificador da entidade.
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AbstractEntity<?> other = (AbstractEntity<?>) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "AbstractEntity [id=" + id + "]";
    }
}