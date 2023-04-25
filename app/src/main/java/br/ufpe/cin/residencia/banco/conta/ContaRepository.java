package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

//Ver anotações TODO no código
public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;
    private LiveData<Double> totalDinheiro;

    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
        this.totalDinheiro = dao.getTotalDinheiro();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    public LiveData<Double> getTotalDinheiroBanco() {
        return totalDinheiro;
    }

    @WorkerThread
    public void inserir(Conta c) {
        dao.adicionar(c);
    }

    @WorkerThread
    public void atualizar(Conta c) {
        dao.atualizar(c);
    }

    @WorkerThread
    public void remover(Conta c) {
        dao.remover(c);
    }

    @WorkerThread
    public List<Conta> buscarPeloNome(String nomeCliente) {
        LiveData<List<Conta>> contasLiveData = dao.buscarPorNomeCliente("%" + nomeCliente + "%");
        List<Conta> contas = contasLiveData.getValue();
        return contas;
    }

    @WorkerThread
    public List<Conta> buscarPeloCPF(String cpfCliente) {
        LiveData<List<Conta>> contasCpfLiveData = dao.buscarPorCpfCliente(cpfCliente);
        List<Conta> contas = contasCpfLiveData.getValue();
        return contas;
    }

    //@WorkerThread
    public LiveData<Conta> buscarPeloNumero(String numeroConta) {
        return Transformations.switchMap(dao.buscarPorNumero(numeroConta), conta -> {
            if (conta == null) {
                return null;
            } else {
                MutableLiveData<Conta> contaLiveData = new MutableLiveData<>();
                contaLiveData.setValue(conta);
                return contaLiveData;
            }
        });
    }

    public Conta buscarPeloNumeroSync(String numeroConta) {
        return dao.buscarPeloNumeroSync(numeroConta);
    }

}
