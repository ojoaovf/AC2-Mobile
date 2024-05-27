package com.example.ac2mb.activity;

import static com.example.ac2mb.R.id.btnCadastro;
import static com.example.ac2mb.R.id.btnListagem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ac2mb.R;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastro, btnListagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastro = findViewById(R.id.btnCadastro);
        btnListagem = findViewById(R.id.btnListagem);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroAlunoActivity.class);
                startActivity(intent);
            }
        });

        btnListagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListagemAlunosActivity.class);
                startActivity(intent);
            }
        });
    }
}
