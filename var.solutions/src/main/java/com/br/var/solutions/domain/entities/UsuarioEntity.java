package com.br.var.solutions.domain.entities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "TabUsuario")
@Entity
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //So pra falar que a coluna id é id mesmo mas não precisa
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "senha")
    private String senha;
}
