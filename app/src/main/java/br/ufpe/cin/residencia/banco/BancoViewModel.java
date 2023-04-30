package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    private final ContaRepository contaRepository;
    private final LiveData<Double> totalDinheiroBanco;
    private final MutableLiveData<Event<String>> mensagemToast = new MutableLiveData<>();

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
                exibirToast("Conta de origem não encontrada.");
                return;
            }

            if (contaOrigem.getSaldo() < valor) {
                exibirToast("Saldo insuficiente na conta de origem.");
                return;
            }

            contaOrigem.debitar(valor);
            contaRepository.atualizar(contaOrigem);

            Conta contaDestino = contaRepository.buscarPeloNumeroSync(numeroContaDestino);
            if (contaDestino == null) {
                exibirToast("Conta de destino não encontrada.");
                return;
            }

            contaDestino.creditar(valor);
            contaRepository.atualizar(contaDestino);

            exibirToast("Transferência realizada com sucesso!");
        });
    }

    void creditar(String numeroConta, double valor) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        contaLiveData.observeForever(new Observer<Conta>() {
            @Override
            public void onChanged(Conta conta) {
                if (conta == null) {
                    exibirToast("Conta não encontrada.");
                    return;
                }
                conta.creditar(valor);
                contaLiveData.removeObserver(this);
                new Thread(() -> contaRepository.atualizar(conta)).start();
                exibirToast("Valor Creditado com Sucesso.");
            }
        });
    }

    void debitar(String numeroConta, double valor) {
        LiveData<Conta> contaLiveData = this.contaRepository.buscarPeloNumero(numeroConta);
        contaLiveData.observeForever(new Observer<Conta>() {
            @Override
            public void onChanged(Conta conta) {
                if (conta == null) {
                    exibirToast("Conta não encontrada.");
                    return;
                }

                if (conta.getSaldo() < valor) {
                    exibirToast("Saldo insuficiente na conta.");
                    return;
                }

                conta.debitar(valor);
                contaLiveData.removeObserver(this);
                new Thread(() -> contaRepository.atualizar(conta)).start();
                exibirToast("Valor Debitado com Sucesso.");
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
        if (contas == null) {
            exibirToast("Conta não encontrada.");
        }
        return contas;
    }


    public LiveData<Double> getTotalDinheiroBanco() {
        return totalDinheiroBanco;
    }

    private void exibirToast(String mensagem) {
        mensagemToast.postValue(new Event<>(mensagem));
    }

    public LiveData<Event<String>> getMensagemToast() {
        return mensagemToast;
    }

}
