package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculaMediaTest {

    private CalculaMedia calculaMedia;

    @BeforeEach
    void setUp() {
        calculaMedia = new CalculaMedia();
    }

    @Test
    @DisplayName("Should return the sum of grades when total media is less than 10")
    void shouldReturnSumWhenMediaIsLessThanTen() {
        double result = calculaMedia.Media(2.0, 3.0, 4.0);
        assertEquals(9.0, result, 0.0001);
    }

    @Test
    @DisplayName("Should return 10 when total media is exactly 10")
    void shouldReturnTenWhenMediaIsExactlyTen() {
        double result = calculaMedia.Media(3.0, 3.0, 4.0);
        assertEquals(10.0, result, 0.0001);
    }

    @Test
    @DisplayName("Should return -1 when total media exceeds 10")
    void shouldReturnMinusOneWhenMediaExceedsTen() {
        double result = calculaMedia.Media(4.0, 4.0, 3.0);
        assertEquals(-1.0, result, 0.0001);
    }

    @Test
    @DisplayName("Should return 0 when all grades are zero")
    void shouldReturnZeroWhenAllGradesAreZero() {
        double result = calculaMedia.Media(0.0, 0.0, 0.0);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    @DisplayName("Should handle decimal values correctly")
    void shouldHandleDecimalValuesCorrectly() {
        double result = calculaMedia.Media(2.5, 3.25, 3.75);
        assertEquals(9.5, result, 0.0001);
    }
}
