package util;

import Dto.NotaDto;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeradordeRelatorioTest {

    private GeradordeRelatorio geradordeRelatorio;

    @Mock
    private HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        geradordeRelatorio = new GeradordeRelatorio();
    }

    @Test
    void testGerarRelatoriocontato_Success() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        List<NotaDto> listagem = new ArrayList<>();
        listagem.add(new NotaDto(8.5, "João Silva", "Matemática", 8.0, 9.0, 8.5));
        listagem.add(new NotaDto(7.0, "Maria Santos", "História", 7.0, 7.0, 7.0));

        assertDoesNotThrow(() -> geradordeRelatorio.gerarRelatoriocontato(response, listagem));

        assertEquals("inline; filename=relatorio.pdf", response.getHeader("Content-Disposition"));
        assertTrue(response.getContentAsByteArray().length > 0);
    }

    @Test
    void testGerarRelatoriocontato_EmptyList() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        List<NotaDto> listagem = new ArrayList<>();

        assertDoesNotThrow(() -> geradordeRelatorio.gerarRelatoriocontato(response, listagem));

        assertEquals("inline; filename=relatorio.pdf", response.getHeader("Content-Disposition"));
        assertTrue(response.getContentAsByteArray().length > 0);
    }

    @Test
    void testGerarRelatoriocontato_IOException() throws Exception {
        when(mockResponse.getOutputStream()).thenThrow(new IOException("Simulated IO Exception"));

        assertDoesNotThrow(() -> geradordeRelatorio.gerarRelatoriocontato(mockResponse, new ArrayList<>()));

        verify(mockResponse).setContentType("application/pdf");
        verify(mockResponse).reset();
        verify(mockResponse).addHeader("Content-Disposition", "inline; filename=relatorio.pdf");
        verify(mockResponse).getOutputStream();
    }
}
