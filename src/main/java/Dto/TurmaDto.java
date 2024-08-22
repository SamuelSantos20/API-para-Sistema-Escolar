package Dto;

import java.util.Date;
import java.util.List;


import org.springframework.format.annotation.DateTimeFormat;

import domain.Aluno;
import domain.Diciplina;


public class TurmaDto {

	private Long id;

	private String nome;

	private String descricao;
	
	private String codigo_turma;
	
	 @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data_inicio; 
	 @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data_fim;
	
	 private List<Aluno> AlunoNomes;
	
	private List<Diciplina> diciplinasNomes;



	public TurmaDto(Long id, String nome, String descricao, String codigo_turma, Date data_inicio, Date data_fim,
			List<Aluno> alunoNomes, List<Diciplina> diciplinasNomes) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.codigo_turma = codigo_turma;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim;
		AlunoNomes = alunoNomes;
		this.diciplinasNomes = diciplinasNomes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Diciplina> getDiciplinasNomes() {
		return diciplinasNomes;
	}

	public void setDiciplinasNomes(List<Diciplina> diciplinasNomes) {
		this.diciplinasNomes = diciplinasNomes;
	}

	public Date getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}

	public Date getData_fim() {
		return data_fim;
	}

	public void setData_fim(Date data_fim) {
		this.data_fim = data_fim;
	}

	public List<Aluno> getAlunoNomes() {
		return AlunoNomes;
	}

	

	public String getCodigo_turma() {
		return codigo_turma;
	}

	public void setCodigo_turma(String codigo_turma) {
		this.codigo_turma = codigo_turma;
	}

	public void setAlunoNomes(List<Aluno> alunoNomes) {
		AlunoNomes = alunoNomes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	

}
