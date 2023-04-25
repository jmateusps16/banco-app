package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
    ContaViewModel viewModel;
    Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);
        viewModel.getContaPorNumero(numeroConta).observe(this, c -> {
            conta = c;
            campoNome.setText(conta.getNomeCliente());
            campoNumero.setText(conta.getNumero());
            campoCPF.setText(conta.getCpfCliente());
            campoSaldo.setText(Double.toString(conta.getSaldo()));
        });

        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    if (nomeCliente.isEmpty() || cpfCliente.isEmpty() || saldoConta.isEmpty()) {
                        if (saldoConta != null && !saldoConta.isEmpty() && Double.parseDouble(saldoConta) == conta.getSaldo()) {
                            Toast.makeText(this, "O saldo precisa ser diferente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_LONG).show();
                        }
                        return;
                    }

                    conta.setNomeCliente(nomeCliente);
                    conta.setCpfCliente(cpfCliente);
                    conta.setSaldo(Double.parseDouble(saldoConta));
                    viewModel.atualizar(conta);
                    finish();
                }
        );

        btnRemover.setOnClickListener(v -> {
            viewModel.remover(conta);
            finish();
        });
    }
}