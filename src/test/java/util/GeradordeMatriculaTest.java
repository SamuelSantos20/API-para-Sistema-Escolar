package util;

import domain.SufixoMatricula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GeradordeMatriculaTest {

    @Test
    @DisplayName("Should generate matricula with prefix of 6 digits and appended suffix")
    void shouldGenerateMatriculaWith6DigitPrefixAndSuffix() {
        String suffix = "ALU";
        String matricula = GeradordeMatricula.generateMatricula(suffix);

        assertNotNull(matricula, "Generated matricula should not be null");
        assertEquals(6 + suffix.length(), matricula.length(), "Matricula length should be 6 + suffix length");
        assertTrue(matricula.endsWith(suffix), "Matricula should end with the given suffix");

        String numericPrefix = matricula.substring(0, 6);
        assertTrue(numericPrefix.matches("\\d{6}"), "Prefix should consist of exactly 6 digits");
    }

    @ParameterizedTest
    @EnumSource(SufixoMatricula.class)
    @DisplayName("Should generate valid matricula for all SufixoMatricula enum values")
    void shouldGenerateMatriculaForEnumSuffixes(SufixoMatricula sufixo) {
        String suffix = sufixo.getSufixo();
        String matricula = GeradordeMatricula.generateMatricula(suffix);

        assertNotNull(matricula);
        assertTrue(matricula.endsWith(suffix));
        assertEquals(6 + suffix.length(), matricula.length());
        assertTrue(matricula.substring(0, 6).matches("\\d{6}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "TEST_123", "@@@"})
    @DisplayName("Should handle various suffix inputs including empty, spaces, and special characters")
    void shouldHandleVariousSuffixInputs(String suffix) {
        String matricula = GeradordeMatricula.generateMatricula(suffix);

        assertNotNull(matricula);
        assertTrue(matricula.endsWith(suffix));
        assertEquals(6 + suffix.length(), matricula.length());
        assertTrue(matricula.substring(0, 6).matches("\\d{6}"));
    }

    @Test
    @DisplayName("Should handle null suffix by appending 'null'")
    void shouldHandleNullSuffix() {
        String matricula = GeradordeMatricula.generateMatricula(null);

        assertNotNull(matricula);
        assertTrue(matricula.endsWith("null"));
        assertEquals(10, matricula.length());
        assertTrue(matricula.substring(0, 6).matches("\\d{6}"));
    }

    @Test
    @DisplayName("Should generate unique matriculas across multiple invocations")
    void shouldGenerateUniqueMatriculas() {
        String suffix = "PROF";
        String matricula1 = GeradordeMatricula.generateMatricula(suffix);
        String matricula2 = GeradordeMatricula.generateMatricula(suffix);

        assertNotNull(matricula1);
        assertNotNull(matricula2);
        // While theoretically possible to collide, 10^6 random values make consecutive identical values extremely unlikely.
        assertNotEquals(matricula1, matricula2);
    }

    @Test
    @DisplayName("Should generate non-null password with geradorSenha")
    void shouldGeneratePassword() {
        Long password = GeradordeMatricula.geradorSenha();

        assertNotNull(password, "Generated password should not be null");
        assertTrue(password >= 0L, "Generated password should be a non-negative number");
    }
}
