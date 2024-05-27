package com.example.ac2mb.api;

import com.example.ac2mb.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlunoService {
    @POST("aluno")
    Call<Aluno> salvarAluno(@Body Aluno aluno);

    @GET("aluno")
    Call<List<Aluno>> listarAlunos();
}


