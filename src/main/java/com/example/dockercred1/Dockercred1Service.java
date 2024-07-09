package com.example.dockercred1;

import org.springframework.stereotype.Service;

@Service
public class Dockercred1Service {
    public double add(double x, double y) {
        return x + y;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
