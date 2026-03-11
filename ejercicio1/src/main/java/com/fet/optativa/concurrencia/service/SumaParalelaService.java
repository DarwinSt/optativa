package com.fet.optativa.concurrencia.service;

import com.fet.optativa.concurrencia.domain.SumaRangoTask;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SumaParalelaService {

    private static final long LIMITE_SUPERIOR = 1_000_000;
    private static final int NUM_HILOS = 4;

    public long calcularSumaParalela() {
        long[] resultadosParciales = new long[NUM_HILOS];
        long paso = LIMITE_SUPERIOR / NUM_HILOS;
        List<Thread> hilos = new ArrayList<>(NUM_HILOS);

        for (int i = 0; i < NUM_HILOS; i++) {
            long inicio = i * paso + 1;
            long fin = (i == NUM_HILOS - 1) ? LIMITE_SUPERIOR : (i + 1) * paso;
            Runnable task = new SumaRangoTask(inicio, fin, resultadosParciales, i);
            Thread hilo = new Thread(task, "Hilo-" + (i + 1));
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupción mientras se esperaba a los hilos", e);
            }
        }

        long sumaTotal = 0;
        for (long parcial : resultadosParciales) {
            sumaTotal += parcial;
        }
        return sumaTotal;
    }
}
