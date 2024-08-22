package Dto;

import java.util.Date;

import domain.Turma;

public class AlunoDto {

	
	private String nome;
	
	private String matricula;
	
	private Date data_nascimento;
	
	private String senha;
	
	private  Turma turma;

	public AlunoDto(String nome, String matricula, Date data_nascimento, String senha, Turma turma) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.data_nascimento = data_nascimento;
		this.senha = senha;
		this.turma = turma;
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

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	
	
	
}
