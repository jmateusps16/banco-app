package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

//Ver métodos anotados com TODO
public class ContaViewModel extends AndroidViewModel {

    final ContaRepository repository;
    public LiveData<List<Conta>> contas;
    final MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
    }

    void inserir(Conta c) {
        new Thread(() -> repository.inserir(c)).start();
    }

    void atualizar(Conta c) {
        new Thread(() -> repository.atualizar(c)).start();
    }

    void remover(Conta c) {
        new Thread(() -> repository.remover(c)).start();
    }

//    void buscarPeloNumero(String numeroConta) {
//        repository.buscarPeloNumero(numeroConta).observeForever(conta -> {
//            _contaAtual.setValue(conta);
//        });
//    }

    public LiveData<Conta> getContaAtual() {
        return _contaAtual;
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

}
