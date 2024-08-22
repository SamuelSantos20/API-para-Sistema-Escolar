package domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;

@Entity
@Table(name = "professor")
@SuppressWarnings("serial")
public class Professor extends AbstractEntity<Long> {

	@Column(name = "nome", length = 200, nullable = false)
	private String nome;

	@Column(name = "matricula", length = 12, nullable = false)
	private String matricula;

	@Column(name = "senha", length = 200, unique = true, updatable = true, nullable = false)
	private String senha;

	@Column(name = "telefone", length = 20, unique = true, nullable = false, updatable = true)
	private String telefone;

	@Column(name = "email", length = 200, unique = true, updatable = true, nullable = false)
	private String email;

	@ManyToMany(mappedBy = "professores")
	private List<Diciplina> diciplinas = new ArrayList<>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<Diciplina> getDiciplinas() {
		return diciplinas;
	}

	public void setDiciplinas(List<Diciplina> diciplinas) {
		this.diciplinas = diciplinas;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
