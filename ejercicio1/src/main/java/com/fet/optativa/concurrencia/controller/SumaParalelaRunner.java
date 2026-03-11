package com.fet.optativa.concurrencia.controller;

import com.fet.optativa.concurrencia.service.SumaParalelaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SumaParalelaRunner implements ApplicationRunner {

    private final SumaParalelaService sumaParalelaService;

    public SumaParalelaRunner(SumaParalelaService sumaParalelaService) {
        this.sumaParalelaService = sumaParalelaService;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("=== Ejercicio 1 – Paralelismo ===");
        System.out.println("Calculando suma de 1 a 1.000.000 con 4 hilos...");
        long inicio = System.currentTimeMillis();

        long sumaTotal = sumaParalelaService.calcularSumaParalela();

        long duracion = System.currentTimeMillis() - inicio;
        System.out.println("Suma total: " + sumaTotal);
        System.out.println("Tiempo: " + duracion + " ms");
        System.out.println("(Fórmula 1+2+...+n = n(n+1)/2 => " + (1_000_000L * (1_000_000L + 1) / 2) + ")");
    }
}
