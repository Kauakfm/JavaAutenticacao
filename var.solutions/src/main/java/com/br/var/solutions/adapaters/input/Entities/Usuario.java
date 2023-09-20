package com.br.var.solutions.adapaters.input.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {
    @Id
    private int id;

    private String nome;

    private String usuario;

    private String senha;
}
