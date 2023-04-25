package br.ufpe.cin.residencia.banco.conta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void adicionar(Conta c);

    @Query("SELECT * FROM contas WHERE numero = :numeroConta")
    LiveData<Conta> buscarPorNumero(String numeroConta);

    @Query("SELECT * FROM contas WHERE numero = :numeroConta")
    Conta buscarPeloNumeroSync(String numeroConta);

    @Query("SELECT * FROM contas WHERE nomeCliente LIKE '%' || :nomeDoCliente || '%' ORDER BY numero ASC")
    LiveData<List<Conta>> buscarPorNomeCliente(String nomeDoCliente);

    @Query("SELECT * FROM contas WHERE cpfCliente = :cpfDoCliente ORDER BY numero ASC")
    LiveData<List<Conta>> buscarPorCpfCliente(String cpfDoCliente);

    @Query("SELECT * FROM contas ORDER BY numero ASC")
    LiveData<List<Conta>> contas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserir(Conta conta);

    @Update
    void atualizar(Conta conta);

    @Delete
    void remover(Conta conta);

    @Query("SELECT SUM(saldo) FROM contas")
    LiveData<Double> getTotalDinheiro();

    @Query("SELECT * FROM contas WHERE numero = :numeroConta")
    LiveData<Conta> buscarConta(String numeroConta);

    @Query("SELECT * FROM contas WHERE nomeCliente LIKE '%' || :nomeDoCliente || '%' ORDER BY numero ASC")
    LiveData<List<Conta>> buscarContasPorNomeCliente(String nomeDoCliente);

    @Query("SELECT * FROM contas WHERE cpfCliente = :cpfDoCliente ORDER BY numero ASC")
    LiveData<List<Conta>> buscarContasPorCPFCliente(String cpfDoCliente);

}
//Ver anotações TODO no código
//TODO incluir métodos para atualizar conta e remover conta
//TODO incluir métodos para buscar pelo (1) número da conta, (2) pelo nome e (3) pelo CPF do Cliente