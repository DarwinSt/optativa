package com.fet.optativa.concurrencia.domain;

public class CuentaBancaria {

    private long saldo;

    public CuentaBancaria(long saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized boolean retirar(long monto) {
        if (monto <= 0) {
            return false;
        }
        if (saldo >= monto) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    public synchronized long getSaldo() {
        return saldo;
    }
}
