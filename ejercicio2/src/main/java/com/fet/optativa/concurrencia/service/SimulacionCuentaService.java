package com.fet.optativa.concurrencia.service;

import com.fet.optativa.concurrencia.domain.ClienteRetiroTask;
import com.fet.optativa.concurrencia.domain.CuentaBancaria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulacionCuentaService {

    private static final long SALDO_INICIAL = 1000;
    private static final long MONTO_RETIRO = 400;
    private static final int NUM_CLIENTES = 3;

    public long ejecutarSimulacion() {
        CuentaBancaria cuenta = new CuentaBancaria(SALDO_INICIAL);
        boolean[] resultados = new boolean[NUM_CLIENTES];

        List<Thread> hilos = new ArrayList<>(NUM_CLIENTES);
        String[] nombres = { "Cliente 1", "Cliente 2", "Cliente 3" };

        for (int i = 0; i < NUM_CLIENTES; i++) {
            Runnable task = new ClienteRetiroTask(nombres[i], MONTO_RETIRO, cuenta, resultados, i);
            Thread hilo = new Thread(task, nombres[i]);
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupción al esperar a los clientes", e);
            }
        }

        return cuenta.getSaldo();
    }
}
