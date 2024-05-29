package com.example.ac2mb.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2mb.R;
import com.example.ac2mb.adapter.AlunoAdapter;
import com.example.ac2mb.api.AlunoService;
import com.example.ac2mb.model.Aluno;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListagemAlunosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlunoAdapter alunoAdapter;
    private AlunoService alunoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_alunos);

        recyclerView = findViewById(R.id.recyclerViewAlunos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://66567a9d9f970b3b36c585db.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alunoService = retrofit.create(AlunoService.class);
        listarAlunos();
    }

    private void listarAlunos() {
        Call<List<Aluno>> call = alunoService.listarAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Aluno> alunos = response.body();
                    alunoAdapter = new AlunoAdapter(alunos);
                    recyclerView.setAdapter(alunoAdapter);
                } else {
                    Toast.makeText(ListagemAlunosActivity.this, "Erro ao carregar alunos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListagemAlunosActivity.this, "Erro ao carregar alunos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}