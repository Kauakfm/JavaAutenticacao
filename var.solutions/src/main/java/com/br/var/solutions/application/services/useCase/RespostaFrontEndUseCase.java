package com.br.var.solutions.application.services.useCase;

import com.br.var.solutions.application.services.entities.InformacoesImc;
import com.br.var.solutions.adapaters.input.Entities.PessoaRequest;
import com.br.var.solutions.adapaters.input.Entities.PessoaResponse;

import java.sql.SQLException;
import java.util.List;

public interface RespostaFrontEndUseCase {
    PessoaResponse mapper(PessoaRequest pessoa, InformacoesImc imc, int anoNascimento, double impostoRenda, String validaMundial, double saldoDolar) throws SQLException;

        List<PessoaResponse> buscaListaPessoas() throws SQLException;

        PessoaResponse buscaDetalhesPessoa(int id) throws SQLException;


        void excluirDadosPessoa(int id) throws SQLException;
}
