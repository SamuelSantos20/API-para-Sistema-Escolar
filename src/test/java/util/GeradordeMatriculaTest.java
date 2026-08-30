package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GeradordeMatriculaTest {

    @Test
    @DisplayName("geradorSenha should return a non-null and non-negative Long")
    void geradorSenha_ReturnsNonNullAndNonNegative() {
        Long senha = GeradordeMatricula.geradorSenha();
        assertNotNull(senha, "Generated password should not be null");
        assertTrue(senha >= 0L, "Generated password should be non-negative");
    }

    @Test
    @DisplayName("geradorSenha should generate different password values across multiple calls")
    void geradorSenha_GeneratesDifferentValues() {
        Set<Long> senhas = new HashSet<>();
        int sampleSize = 50;

        for (int i = 0; i < sampleSize; i++) {
            Long senha = GeradordeMatricula.geradorSenha();
            assertNotNull(senha);
            assertTrue(senha >= 0L);
            senhas.add(senha);
        }

        // Verify that randomness produces a set of unique values (most if not all should be unique)
        assertTrue(senhas.size() > 1, "Generated passwords should vary across multiple invocations");
    }

    @Test
    @DisplayName("generateMatricula should generate matricula ending with specified type")
    void generateMatricula_EndsWithType() {
        String type = "ADM";
        String matricula = GeradordeMatricula.generateMatricula(type);

        assertNotNull(matricula, "Generated matricula should not be null");
        assertTrue(matricula.endsWith(type), "Matricula should end with type suffix");
        assertEquals(6 + type.length(), matricula.length(), "Matricula length should be ID_LENGTH (6) plus type length");
    }

    @Test
    @DisplayName("generateMatricula prefix should consist of 6 digits")
    void generateMatricula_PrefixIsNumericDigits() {
        String type = "ALUNO";
        String matricula = GeradordeMatricula.generateMatricula(type);

        String numericPrefix = matricula.substring(0, 6);
        assertTrue(numericPrefix.matches("\\d{6}"), "Prefix should be 6 numeric digits");
    }

    @Test
    @DisplayName("generateMatricula should generate unique values across multiple calls")
    void generateMatricula_GeneratesDifferentValues() {
        Set<String> matriculas = new HashSet<>();
        int sampleSize = 50;
        String type = "PROF";

        for (int i = 0; i < sampleSize; i++) {
            String matricula = GeradordeMatricula.generateMatricula(type);
            assertNotNull(matricula);
            matriculas.add(matricula);
        }

        assertTrue(matriculas.size() > 1, "Generated matriculas should vary across multiple invocations");
    }
}
