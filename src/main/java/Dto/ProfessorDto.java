package Dto;

import java.util.Set;

public class ProfessorDto {

	private String nome;
	
	private String matricula;
	
	private String senha;
	
	private String telefone;
	
	private String email;
	
	private Set<String> diciplina;

	public ProfessorDto(String nome, String matricula, String senha, String telefone, String email,
			Set<String> diciplina) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.senha = senha;
		this.telefone = telefone;
		this.email = email;
		this.diciplina = diciplina;
	}
	
	

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

	public Set<String> getDiciplina() {
		return diciplina;
	}

	public void setDiciplina(Set<String> diciplina) {
		this.diciplina = diciplina;
	}
	
	
}
