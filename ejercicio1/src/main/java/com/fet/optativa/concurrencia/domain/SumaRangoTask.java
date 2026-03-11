package com.fet.optativa.concurrencia.domain;

public class SumaRangoTask implements Runnable {

    private final long inicio;
    private final long fin;
    private final long[] resultados;
    private final int indice;

    public SumaRangoTask(long inicio, long fin, long[] resultados, int indice) {
        this.inicio = inicio;
        this.fin = fin;
        this.resultados = resultados;
        this.indice = indice;
    }

    @Override
    public void run() {
        long sumaParcial = 0;
        for (long i = inicio; i <= fin; i++) {
            sumaParcial += i;
        }
        resultados[indice] = sumaParcial;
    }
}
