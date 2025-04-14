package org.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    public void testNumbersEquality() {
        int a = 5;
        int b = 5;

        // Проверка на равенство
        assertEquals(a, b, "Числа должны быть равны");
    }
}
