package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "turma")
@Entity
public class Turma extends AbstractEntity<Long> {

	@Column(name = "nome", length = 200, nullable = false)
	private String nome;
    @Column(name = "codigo_turma" , nullable = false , length = 100 , unique = true)
	private String codigo_turma;
	@Column(name = "descricao", length = 400, nullable = false, updatable = true)
	private String descricao;

	@Column(name = "data_inicio", updatable = true, nullable = false, length = 14)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data_inicio;

	@Column(name = "data_fim", length = 14, nullable = false, updatable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data_fim;

	@OneToMany(mappedBy = "turmaId")
	private List<Aluno> alunos = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "turma_diciplina", joinColumns = @JoinColumn(name = "turma_id"), inverseJoinColumns = @JoinColumn(name = "diciplina_id"))
	private List<Diciplina> Diciplinas = new ArrayList<>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Diciplina> getDiciplinas() {
		return Diciplinas;
	}

	public void setDiciplinas(List<Diciplina> diciplinas) {
		this.Diciplinas = diciplinas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getCodigo_turma() {
		return codigo_turma;
	}

	public void setCodigo_turma(String codigo_turma) {
		this.codigo_turma = codigo_turma;
	}

	
}
