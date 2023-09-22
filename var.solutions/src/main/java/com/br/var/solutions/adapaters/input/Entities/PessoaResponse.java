package com.br.var.solutions.adapaters.input.Entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class PessoaResponse {

    public int id;
    public String nome;
    public int idade;
    public double imc;
    public String classificacaoIMC;
    public double saldoEmDolar;
    public String sobrenome;
    public String endereco;
    public int anoNascimento;
    public double peso;
    public String time;
    public double salario;
    public double altura;
    public double Saldo;
    public String mundialClubes;
    public double ir;
    public double aliquota;
    public LocalDate dtNascimento;
    public int UserId;

}
