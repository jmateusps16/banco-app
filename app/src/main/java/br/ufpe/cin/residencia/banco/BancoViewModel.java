package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

public class BancoViewModel extends AndroidViewModel {
    private ContaRepository contaRepository;
    private LiveData<Double> totalDinheiroBanco;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.contaRepository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.totalDinheiroBanco = this.contaRepository.getTotalDinheiroBanco();
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            Conta contaOrigem = contaRepository.buscarPeloNumeroSync(numeroContaOrigem);
            if (contaOrigem == null) {
                throw new RuntimeException("Conta de origem não encontrada.");
            }

            if (contaOrigem.getSaldo() < valor) {
                throw new RuntimeException("Saldo insuficiente na conta de origem.");
            }

            contaOrigem.debitar(valor);
            contaRepository.atualizar(contaOrigem);

            Conta contaDestino = contaRepository.buscarPeloNumeroSync(numeroContaDestino);
            if (contaDestino == null) {
                throw new RuntimeException("Conta de destino não encontrada.");
            }

            contaDestino.creditar(valor);
            contaRepository.atualizar(contaDestino);
        });
    }

    void creditar(String numeroConta, double valor) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        contaLiveData.observeForever(new Observer<Conta>() {
            @Override
            public void onChanged(Conta conta) {
                if (conta == null) {
                    throw new RuntimeException("Conta não encontrada.");
                }
                conta.creditar(valor);
                contaLiveData.removeObserver(this);
                new Thread(() -> contaRepository.atualizar(conta)).start();
            }
        });
    }

    void debitar(String numeroConta, double valor) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        contaLiveData.observeForever(new Observer<Conta>() {
            @Override
            public void onChanged(Conta conta) {
                if (conta == null) {
                    throw new RuntimeException("Conta não encontrada.");
                }

                if (conta.getSaldo() < valor) {
                    throw new RuntimeException("Saldo insuficiente na conta.");
                }

                conta.debitar(valor);
                contaLiveData.removeObserver(this);
                new Thread(() -> contaRepository.atualizar(conta)).start();
            }
        });
    }

    LiveData<List<Conta>> buscarPeloNome(String nomeCliente) {
        return this.contaRepository.buscarPeloNome(nomeCliente);
    }

    LiveData<List<Conta>> buscarPeloCPF(String cpfCliente) {
        return this.contaRepository.buscarPeloCPF(cpfCliente);
    }

    LiveData<Conta> buscarPeloNumero(String numeroConta) {
        return this.contaRepository.buscarPeloNumero(numeroConta);
    }

    public LiveData<List<Conta>> buscar(String numeroConta, String nomeTitular, String cpfTitular) {
        String[] args = new String[3];
        args[0] = "%" + numeroConta + "%";
        args[1] = "%" + nomeTitular + "%";
        args[2] = "%" + cpfTitular + "%";
        LiveData<List<Conta>> contas = this.contaRepository.buscar(args[0], args[1], args[2]);
        return contas;
    }


    public LiveData<Double> getTotalDinheiroBanco() {
        return totalDinheiroBanco;
    }
}
