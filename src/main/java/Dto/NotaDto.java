package Dto;



public class NotaDto {

	private double media;

	private String nome_aluno;

	private String diciplina_nome;

	private double nota_trabalho;

	private double nota_teste;

	private double nota_prova;

	public NotaDto(double media, String string, String string2, double nota_trabalho, double nota_teste,
			double nota_prova) {
		super();
		this.media = media;
		this.nome_aluno = string;
		this.diciplina_nome = string2;
		this.nota_trabalho = nota_trabalho;
		this.nota_teste = nota_teste;
		this.nota_prova = nota_prova;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public String getNome_aluno() {
		return nome_aluno;
	}

	public void setNome_aluno(String nome_aluno) {
		this.nome_aluno = nome_aluno;
	}

	public String getDiciplina_nome() {
		return diciplina_nome;
	}

	public void setDiciplina_nome(String diciplina_nome) {
		this.diciplina_nome = diciplina_nome;
	}

	public double getNota_trabalho() {
		return nota_trabalho;
	}

	public void setNota_trabalho(double nota_trabalho) {
		this.nota_trabalho = nota_trabalho;
	}

	public double getNota_teste() {
		return nota_teste;
	}

	public void setNota_teste(double nota_teste) {
		this.nota_teste = nota_teste;
	}

	public double getNota_prova() {
		return nota_prova;
	}

	public void setNota_prova(double nota_prova) {
		this.nota_prova = nota_prova;
	}

}
