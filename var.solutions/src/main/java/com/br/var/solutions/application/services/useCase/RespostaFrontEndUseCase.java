package com.br.var.solutions.application.services.useCase;

import com.br.var.solutions.domain.entities.InformacoesImc;
import com.br.var.solutions.adapaters.input.Entities.PessoaRequest;
import com.br.var.solutions.adapaters.input.Entities.PessoaResponse;

public interface RespostaFrontEndUseCase {
    PessoaResponse responderFrontEnd (PessoaRequest pessoa, InformacoesImc imc, int anoNascimento, String impostoRenda, String validaMundial, String saldoDolar);
}
