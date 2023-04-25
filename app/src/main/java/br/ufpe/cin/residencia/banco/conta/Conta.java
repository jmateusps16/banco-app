package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//ESTA CLASSE NAO PRECISA SER MODIFICADA!
@Entity(tableName = "contas")
public class Conta {
    @PrimaryKey
    @NonNull
    public String numero;
    public double saldo;
    @NonNull
    public String nomeCliente;
    @NonNull
    public String cpfCliente;

    public Conta(@NonNull String numero, double saldo, @NonNull String nomeCliente, @NonNull String cpfCliente) {
        this.numero = numero;
        this.saldo = saldo;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
    }

    public void creditar(double valor) {
        saldo = saldo + valor;
    }

    public void transferir(Conta c, double v) {
        this.debitar(v);
        c.creditar(v);
    }

    public void debitar(double valor) {
        saldo = saldo - valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getNumero() {
        return numero;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

}
