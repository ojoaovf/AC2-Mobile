package com.example.ac2mb.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ac2mb.R;
import com.example.ac2mb.api.AlunoService;
import com.example.ac2mb.api.ViaCepService;
import com.example.ac2mb.model.Aluno;
import com.example.ac2mb.model.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroAlunoActivity extends AppCompatActivity {
    private EditText etRa, etNome, etCep, etLogradouro, etComplemento, etBairro, etCidade, etUf;
    private Button btnBuscarCep, btnSalvar;

    private ViaCepService viaCepService;
    private AlunoService alunoService;
    private Retrofit retrofitViaCep, retrofitMockApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        etRa = findViewById(R.id.etRa);
        etNome = findViewById(R.id.etNome);
        etCep = findViewById(R.id.etCep);
        etLogradouro = findViewById(R.id.etLogradouro);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUf = findViewById(R.id.etUf);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        btnSalvar = findViewById(R.id.btnSalvar);

        retrofitViaCep = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitMockApi = new Retrofit.Builder()
                .baseUrl("https://664de54aede9a2b5565559e3.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofitViaCep.create(ViaCepService.class);
        alunoService = retrofitMockApi.create(AlunoService.class);

        btnBuscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCep();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CadastroAlunoActivity", "onDestroy called");
    }

    private void buscarCep() {
        String cep = etCep.getText().toString().trim();
        if (!cep.isEmpty() && cep.length() == 8) {  // Verifique se o CEP tem 8 caracteres
            Call<Endereco> call = viaCepService.getEndereco(cep);
            call.enqueue(new Callback<Endereco>() {
                @Override
                public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Endereco endereco = response.body();
                        etLogradouro.setText(endereco.getLogradouro());
                        etComplemento.setText(endereco.getComplemento());
                        etBairro.setText(endereco.getBairro());
                        etCidade.setText(endereco.getLocalidade());
                        etUf.setText(endereco.getUf());
                    } else {
                        Toast.makeText(CadastroAlunoActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Endereco> call, Throwable t) {
                    Toast.makeText(CadastroAlunoActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(CadastroAlunoActivity.this, "CEP inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarAluno() {
        Log.d("salvarAluno", "Iniciando método salvarAluno");

        String raText = etRa.getText().toString().trim();
        String nome = etNome.getText().toString().trim();
        String cep = etCep.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String complemento = etComplemento.getText().toString().trim();
        String bairro = etBairro.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String uf = etUf.getText().toString().trim();

        if (raText.isEmpty() || nome.isEmpty() || cep.isEmpty() || logradouro.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || uf.isEmpty()) {
            Toast.makeText(CadastroAlunoActivity.this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        int ra = Integer.parseInt(raText);

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);
        Log.d("salvarAluno", "Aluno criado: " + aluno);

        Call<Aluno> call = alunoService.salvarAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Log.d("salvarAluno", "Aluno salvo com sucesso");
                    Toast.makeText(CadastroAlunoActivity.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
                    // Limpar os campos após salvar
                    etRa.setText("");
                    etNome.setText("");
                    etCep.setText("");
                    etLogradouro.setText("");
                    etComplemento.setText("");
                    etBairro.setText("");
                    etCidade.setText("");
                    etUf.setText("");
                } else {
                    Log.e("salvarAluno", "Erro ao salvar aluno: " + response.message());
                    Toast.makeText(CadastroAlunoActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e("salvarAluno", "Erro ao salvar aluno: ", t);
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
