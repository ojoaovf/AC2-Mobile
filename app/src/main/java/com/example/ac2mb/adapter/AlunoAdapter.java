package com.example.ac2mb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2mb.R;
import com.example.ac2mb.model.Aluno;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.tvRa.setText("RA: " + aluno.getRa());
        holder.tvNome.setText("Nome: " + aluno.getNome());
        holder.tvEndereco.setText("Endereço: " + aluno.getLogradouro() + ", " + aluno.getComplemento() + " - " + aluno.getBairro() + ", " + aluno.getCidade() + " - " + aluno.getUf());
        holder.tvCep.setText("CEP: " + aluno.getCep());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView tvRa, tvNome, tvEndereco, tvCep;

        AlunoViewHolder(View itemView) {
            super(itemView);
            tvRa = itemView.findViewById(R.id.tvRa);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvEndereco = itemView.findViewById(R.id.tvEndereco);
            tvCep = itemView.findViewById(R.id.tvCep);
        }
    }
}
