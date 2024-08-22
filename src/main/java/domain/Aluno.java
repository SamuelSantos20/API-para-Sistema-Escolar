package domain;


import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@SuppressWarnings("serial")
@Table(name = "aluno")
public class Aluno extends AbstractEntity<Long> {

	@Column(name = "nome", length = 200, nullable = false)
	private String nome;

	@Column(name = "matricula", length = 12, nullable = true, unique = false)
	private String matricula;

	
	@ManyToOne
	@JoinColumn(name = "turma_id")
	Turma turmaId ;

	@Column(name = "datanascimento", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;

	@Column(name = "senha" , length = 200 ,  unique = true , updatable = true , nullable = false)
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



	public Turma getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(Turma turmaId) {
		this.turmaId = turmaId;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	
}
