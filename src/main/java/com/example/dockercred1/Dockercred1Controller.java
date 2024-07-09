package com.example.dockercred1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dockercred")
public class Dockercred1Controller {

    @Autowired
    private Dockercred1Service dockercred1Service;

    @GetMapping("/add")
    public double add(@RequestParam double a, @RequestParam double b) {
        return dockercred1Service.add(a, b);
    }

    @GetMapping("/subtract")
    public double subtract(@RequestParam double a, @RequestParam double b) {
        return dockercred1Service.subtract(a, b);
    }

    @GetMapping("/multiply")
    public double multiply(@RequestParam double a, @RequestParam double b) {
        return dockercred1Service.multiply(a, b);
    }

    @GetMapping("/divide")
    public double divide(@RequestParam double a, @RequestParam double b) {
        return dockercred1Service.divide(a, b);
    }
}
