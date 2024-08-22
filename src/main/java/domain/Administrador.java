package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "administrador")
public class Administrador extends AbstractEntity<Long> {

	@Column(name = "nome", length = 200, nullable = false, updatable = true)
	private String nome;

	@Column(name = "matricula", unique = true, length = 13, nullable = false, updatable = false)
	private String matricula;

	@Column(name = "email", length = 200, unique = true, nullable = false, updatable = true)
	private String email;

	@Column(name = "telefone", length = 20, nullable = false, unique = false, updatable = true)
	private String telefone;
	
	@Column(name = "senha" , length = 200 ,  unique = true , updatable = true , nullable =  false)
	private String senha;
	

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	 

}
