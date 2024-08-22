package domain;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "diciplina")
@SuppressWarnings("serial")
public class Diciplina extends AbstractEntity<Long> {

	@Column(name = "nome", length = 150, unique = true, nullable = false)
	private String nome;

	
	@ManyToMany
	@JoinTable(name ="diciplina_Professor" ,joinColumns =    @JoinColumn(name = "professro_id") , inverseJoinColumns = @JoinColumn(name = "diciplina_id"))
	private List<Professor> professores = new ArrayList<>();

	
	@ManyToMany(mappedBy = "Diciplinas")
	private List<Turma> Turmas = new ArrayList<>();  

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Professor> getProfessor() {
		return professores;
	}

	public void setProfessor(List<Professor> professor) {
		this.professores = professor;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public List<Turma> getTurmas() {
		return Turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		Turmas = turmas;
	}



	
	
	
	
	
}
