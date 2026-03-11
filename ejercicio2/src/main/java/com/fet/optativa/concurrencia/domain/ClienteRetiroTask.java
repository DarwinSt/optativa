package com.fet.optativa.concurrencia.domain;

public class ClienteRetiroTask implements Runnable {

    private final String nombreCliente;
    private final long montoRetiro;
    private final CuentaBancaria cuenta;
    private final boolean[] resultados;
    private final int indice;

    public ClienteRetiroTask(String nombreCliente, long montoRetiro,
                             CuentaBancaria cuenta,
                             boolean[] resultados, int indice) {
        this.nombreCliente = nombreCliente;
        this.montoRetiro = montoRetiro;
        this.cuenta = cuenta;
        this.resultados = resultados;
        this.indice = indice;
    }

    @Override
    public void run() {
        boolean exito = cuenta.retirar(montoRetiro);
        resultados[indice] = exito;
        if (exito) {
            System.out.println(nombreCliente + " retiró " + montoRetiro + ". Saldo restante: " + cuenta.getSaldo());
        } else {
            System.out.println(nombreCliente + " no pudo retirar " + montoRetiro + " (saldo insuficiente: " + cuenta.getSaldo() + ")");
        }
    }
}
