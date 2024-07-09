package com.example.dockercred1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class Dockercred1ApplicationTests {



    @Autowired
    private Dockercred1Service dockercred1Service;

    @Test
    void contextLoads() {
    }

    @Test
    void testAdd() {
        assertEquals(6, dockercred1Service.add(3, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, dockercred1Service.subtract(3, 2));
    }

    @Test
    void testMultiply() {
        assertEquals(6, dockercred1Service.multiply(2, 3));
    }

    @Test
    void testDivide() {
        assertEquals(2, dockercred1Service.divide(6, 3));
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dockercred1Service.divide(1, 0);
        });
        assertEquals("Division by zero is not allowed.", exception.getMessage());
    }
}



