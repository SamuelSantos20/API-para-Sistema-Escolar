package util;


import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Dto.NotaDto;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class GeradordeRelatorio {

	

	
	


	public void gerarRelatoriocontato(HttpServletResponse response ,   List<NotaDto> listagem) {

	    // Criação do documento PDF
	    Document documento = new Document();

	    try {
	        // Definindo o tipo de documento e nome do arquivo
	        response.setContentType("application/pdf");
	        response.reset();
	        response.addHeader("Content-Disposition", "inline; filename=relatorio.pdf");

	        // Criando o escritor de PDF
	        PdfWriter.getInstance(documento, response.getOutputStream());

	        // Abrindo o documento para adicionar conteúdo
	        documento.open();
	        documento.add(new Paragraph("Relatorio Boletim:"));
	        documento.add(new Paragraph("  "));

	        // Criando uma tabela com 6 colunas
	        PdfPTable tabela = new PdfPTable(6);

	        // Cabeçalhos das colunas
	        PdfPCell col1 = new PdfPCell(new Paragraph("Nome do Aluno"));
	        PdfPCell col2 = new PdfPCell(new Paragraph("Disciplina"));
	        PdfPCell col3 = new PdfPCell(new Paragraph("Média Final"));
	        PdfPCell col4 = new PdfPCell(new Paragraph("Nota de Trabalho"));
	        PdfPCell col5 = new PdfPCell(new Paragraph("Nota do Teste"));
	        PdfPCell col6 = new PdfPCell(new Paragraph("Nota Prova"));

	        // Adicionando os cabeçalhos na tabela
	        tabela.addCell(col1);
	        tabela.addCell(col2);
	        tabela.addCell(col3);
	        tabela.addCell(col4);
	        tabela.addCell(col5);
	        tabela.addCell(col6);

	        // Populando a tabela com os dados
	        List<NotaDto> lista = listagem;

	        for (NotaDto nota : lista) {
	            tabela.addCell(nota.getNome_aluno());//adicionando o nome do aluno
	            tabela.addCell(nota.getDiciplina_nome());//adicionando o nome da diciplina
	            tabela.addCell(String.valueOf(nota.getMedia()));  // Convertendo double para String
	            tabela.addCell(String.valueOf(nota.getNota_trabalho()));  // Convertendo double para String
	            tabela.addCell(String.valueOf(nota.getNota_teste()));  // Convertendo double para String
	            tabela.addCell(String.valueOf(nota.getNota_prova()));  // Convertendo double para String
	        }

	        // Adicionando a tabela ao documento
	        documento.add(tabela);

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Fechando o documento
	        documento.close();
	    }
	}

}
