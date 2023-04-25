package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository contaRepository;
    private LiveData<Double> totalDinheiroBanco;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.contaRepository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.totalDinheiroBanco = this.contaRepository.getTotalDinheiroBanco();
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        LiveData<Conta> contaLiveDataOrigem = this.contaRepository.buscarPeloNumero(numeroContaOrigem);
        Conta contaOrigem = contaLiveDataOrigem.getValue();
        LiveData<Conta> contaLiveDataDestino = this.contaRepository.buscarPeloNumero(numeroContaDestino);
        Conta contaDestino = contaLiveDataDestino.getValue();

        if (contaOrigem == null) {
            throw new RuntimeException("Conta de origem não encontrada.");
        }

        if (contaDestino == null) {
            throw new RuntimeException("Conta de destino não encontrada.");
        }

        if (contaOrigem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta de origem.");
        }

        contaOrigem.debitar(valor);
        contaDestino.creditar(valor);

        this.contaRepository.atualizar(contaOrigem);
        this.contaRepository.atualizar(contaDestino);
    }

    void creditar(String numeroConta, double valor) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        Conta conta = contaLiveData.getValue();

        if (conta == null) {
            throw new RuntimeException("Conta não encontrada.");
        }

        conta.creditar(valor);
        this.contaRepository.atualizar(conta);
    }

    void debitar(String numeroConta, double valor) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        Conta conta = contaLiveData.getValue();

        if (conta == null) {
            throw new RuntimeException("Conta não encontrada.");
        }

        if (conta.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta.");
        }

        conta.debitar(valor);
        this.contaRepository.atualizar(conta);
    }

    List<Conta> buscarPeloNome(String nomeCliente) {
        return this.contaRepository.buscarPeloNome(nomeCliente);
    }

    List<Conta> buscarPeloCPF(String cpfCliente) {
        return this.contaRepository.buscarPeloCPF(cpfCliente);
    }

    Conta buscarPeloNumero(String numeroConta) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        Conta conta = contaLiveData.getValue();
        return conta;
    }

    public LiveData<Double> getTotalDinheiroBanco() {
        return totalDinheiroBanco;
    }
}
