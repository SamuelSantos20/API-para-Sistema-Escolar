



package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "nota")
public class Nota extends AbstractEntity<Long> {

	 @Column(name = "valor", length = 10, nullable = false)
	private double media;

	@ManyToOne
	@JoinColumn(name = "aluno_id")
	private Aluno aluno_id;

	@ManyToOne
	@JoinColumn(name = "diciplina_id")
	private Diciplina diciplina_id;

	@Column(name = "nota_trabalho", nullable = false)
	private double nota_trabalho;

	@Column(name = "nota_Teste", nullable = false )
	private double nota_Teste;

	@Column(name = "nota_prova", nullable = false)
	private double nota_prova;

	public Aluno getAluno_id() {
		return aluno_id;
	}

	public void setAluno_id(Aluno aluno_id) {
		this.aluno_id = aluno_id;
	}

	public Diciplina getDiciplina_id() {
		return diciplina_id;
	}

	public void setDiciplina_id(Diciplina diciplina_id) {
		this.diciplina_id = diciplina_id;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public double getNota_trabalho() {
		return nota_trabalho;
	}

	public void setNota_trabalho(double nota_trabalho) {
		this.nota_trabalho = nota_trabalho;
	}

	public double getNota_Teste() {
		return nota_Teste;
	}

	public void setNota_Teste(double nota_Teste) {
		this.nota_Teste = nota_Teste;
	}

	public double getNota_prova() {
		return nota_prova;
	}

	public void setNota_prova(double nota_prova) {
		this.nota_prova = nota_prova;
	}

}
