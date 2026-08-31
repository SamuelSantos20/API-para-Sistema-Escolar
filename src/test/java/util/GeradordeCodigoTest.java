package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeradordeCodigoTest {

    private GeradordeCodigo geradordeCodigo;

    @BeforeEach
    void setUp() {
        geradordeCodigo = new GeradordeCodigo();
    }

    @Test
    @DisplayName("Should generate code with correct length of 4 characters")
    void geradorCodigo_ShouldReturnCodeWithCorrectLength() {
        String code = geradordeCodigo.GeradorCodigo();
        assertNotNull(code);
        assertEquals(4, code.length());
    }

    @Test
    @DisplayName("Should generate code ending with suffix 'T'")
    void geradorCodigo_ShouldEndWithTSuffix() {
        String code = geradordeCodigo.GeradorCodigo();
        assertTrue(code.endsWith("T"));
    }

    @Test
    @DisplayName("Should generate first 3 characters as digits")
    void geradorCodigo_ShouldHaveThreeDigitsPrefix() {
        String code = geradordeCodigo.GeradorCodigo();
        String prefix = code.substring(0, 3);
        assertTrue(prefix.matches("\\d{3}"));
    }

    @Test
    @DisplayName("Should generate valid codes repeatedly")
    void geradorCodigo_ShouldGenerateValidCodesRepeatedly() {
        for (int i = 0; i < 100; i++) {
            String code = geradordeCodigo.GeradorCodigo();
            assertNotNull(code);
            assertEquals(4, code.length());
            assertTrue(code.endsWith("T"));
            assertTrue(code.substring(0, 3).matches("\\d{3}"));
        }
    }
}
