package com.fet.optativa.concurrencia.controller;

import com.fet.optativa.concurrencia.service.SimulacionCuentaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SimulacionCuentaRunner implements ApplicationRunner {

    private final SimulacionCuentaService simulacionCuentaService;

    public SimulacionCuentaRunner(SimulacionCuentaService simulacionCuentaService) {
        this.simulacionCuentaService = simulacionCuentaService;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("=== Ejercicio 2 – Concurrencia (Cuenta bancaria) ===");
        System.out.println("Saldo inicial: 1000. Tres clientes intentan retirar 400 cada uno.");
        System.out.println();

        long saldoFinal = simulacionCuentaService.ejecutarSimulacion();

        System.out.println();
        System.out.println("Saldo final: " + saldoFinal);
        System.out.println("(Se espera 200: solo 2 retiros exitosos de 400, el tercero sin saldo suficiente)");
    }
}
