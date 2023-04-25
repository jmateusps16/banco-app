package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

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

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(v -> {
            String nomeCliente = campoNome.getText().toString();
            String cpfCliente = campoCPF.getText().toString();
            String numeroConta = campoNumero.getText().toString();
            String saldoConta = campoSaldo.getText().toString();

            if (nomeCliente.isEmpty() || cpfCliente.isEmpty() || numeroConta.isEmpty() || saldoConta.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!TextUtils.isDigitsOnly(numeroConta)) {
                campoNumero.setError("Número da conta inválido");
                return;
            }

            Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
            viewModel.inserir(c);
            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}