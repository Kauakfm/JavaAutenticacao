package com.br.var.solutions.domain.entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Table(name = "Tabpessoa")
@Entity
public class PessoaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "dataNascimento")
    private LocalDate dtNascimento;

    @Column(name = "altura")
    private double altura;

    @Column(name = "peso")
    private double peso;

    @Column(name = "salario")
    private double salario;

    @Column(name = "saldo")
    private double saldo;

    @Column(name = "idade")
    private int idade;

    @Column(name = "imc")
    private double imc;

    @Column(name = "classificacao")
    private String classificacao;

    @Column(name = "inss")
    private double inss;

    @Column(name = "aliquota")
    private double aliquota;

    @Column(name = "salarioLiquido")
    private double salarioLiquido;

    @Column(name = "saldoDolar")
    private double saldoDolar;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private UsuarioEntity usuario;

}
