package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collections;

import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        Button btnLimpar = findViewById(R.id.btn_Limpar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString();
                    switch (tipoPesquisa.getCheckedRadioButtonId()) {
                        case R.id.peloNumeroConta:
                            viewModel.buscarPeloNumero(oQueFoiDigitado).observe(this, conta -> {
                                adapter.setContas(Collections.singletonList(conta));
                            });
                            break;
                        case R.id.peloNomeCliente:
                            viewModel.buscarPeloNome(oQueFoiDigitado).observe(this, contas -> {
                                adapter.setContas(contas);
                            });
                            break;
                        case R.id.peloCPFcliente:
                            viewModel.buscarPeloCPF(oQueFoiDigitado).observe(this, contas -> {
                                adapter.setContas(contas);
                            });
                            break;
                        default:
                            break;
                    }
                }
        );

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aPesquisar.setText("");
                adapter.setContas(new ArrayList<>());
            }
        });
    }
}