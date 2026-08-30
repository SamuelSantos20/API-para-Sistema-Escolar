package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import Dto.NotaDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;

class GeradordeRelatorioTest {

    private GeradordeRelatorio geradordeRelatorio;

    @BeforeEach
    void setUp() {
        geradordeRelatorio = new GeradordeRelatorio();
    }

    @Test
    void testGerarRelatoriocontato_WithData() throws Exception {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(baos);
        Mockito.when(response.getOutputStream()).thenReturn(servletOutputStream);

        List<NotaDto> listagem = new ArrayList<>();
        listagem.add(new NotaDto(8.5, "João Silva", "Matemática", 9.0, 8.0, 8.5));
        listagem.add(new NotaDto(7.0, "Maria Santos", "História", 7.5, 6.5, 7.0));

        geradordeRelatorio.gerarRelatoriocontato(response, listagem);

        Mockito.verify(response).setContentType("application/pdf");
        Mockito.verify(response).reset();
        Mockito.verify(response).addHeader("Content-Disposition", "inline; filename=relatorio.pdf");

        byte[] pdfBytes = baos.toByteArray();
        assertTrue(pdfBytes.length > 0, "PDF byte array should not be empty");

        PdfReader reader = new PdfReader(pdfBytes);
        assertEquals(1, reader.getNumberOfPages());

        String text = PdfTextExtractor.getTextFromPage(reader, 1);

        assertTrue(text.contains("Relatorio Boletim:"));
        assertTrue(text.contains("Nome do"));
        assertTrue(text.contains("Aluno"));
        assertTrue(text.contains("Disciplina"));
        assertTrue(text.contains("Média Final"));
        assertTrue(text.contains("João Silva"));
        assertTrue(text.contains("Matemática"));
        assertTrue(text.contains("Maria"));
        assertTrue(text.contains("Santos"));
        assertTrue(text.contains("História"));

        reader.close();
    }

    @Test
    void testGerarRelatoriocontato_EmptyList() throws Exception {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(baos);
        Mockito.when(response.getOutputStream()).thenReturn(servletOutputStream);

        List<NotaDto> listagem = new ArrayList<>();

        geradordeRelatorio.gerarRelatoriocontato(response, listagem);

        Mockito.verify(response).setContentType("application/pdf");
        Mockito.verify(response).reset();
        Mockito.verify(response).addHeader("Content-Disposition", "inline; filename=relatorio.pdf");

        byte[] pdfBytes = baos.toByteArray();
        assertTrue(pdfBytes.length > 0, "PDF byte array should not be empty");

        PdfReader reader = new PdfReader(pdfBytes);
        assertEquals(1, reader.getNumberOfPages());

        String text = PdfTextExtractor.getTextFromPage(reader, 1);
        assertTrue(text.contains("Relatorio Boletim:"));

        reader.close();
    }

    @Test
    void testGerarRelatoriocontato_ExceptionHandling() throws Exception {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getOutputStream()).thenThrow(new IOException("Stream error"));

        List<NotaDto> listagem = new ArrayList<>();

        geradordeRelatorio.gerarRelatoriocontato(response, listagem);

        Mockito.verify(response).setContentType("application/pdf");
        Mockito.verify(response).reset();
        Mockito.verify(response).addHeader("Content-Disposition", "inline; filename=relatorio.pdf");
    }

    private static class DelegatingServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream target;

        public DelegatingServletOutputStream(ByteArrayOutputStream target) {
            this.target = target;
        }

        @Override
        public void write(int b) throws IOException {
            target.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            target.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }
}
