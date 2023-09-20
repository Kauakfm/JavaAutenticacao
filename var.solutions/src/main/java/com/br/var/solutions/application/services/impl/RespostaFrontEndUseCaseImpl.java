package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.application.services.entities.InformacoesImc;
import com.br.var.solutions.adapaters.input.Entities.PessoaRequest;
import com.br.var.solutions.adapaters.input.Entities.PessoaResponse;
import com.br.var.solutions.application.services.useCase.RespostaFrontEndUseCase;
import org.springframework.stereotype.Service;

@Service
public class RespostaFrontEndUseCaseImpl implements RespostaFrontEndUseCase {

    public PessoaResponse responderFrontEnd (PessoaRequest pessoa, InformacoesImc imc, int anoNascimento, String impostoRenda, String validaMundial, String saldoDolar)
    {
        return  montarRespostaFrontEnd (pessoa,  imc,  anoNascimento,  impostoRenda, validaMundial, saldoDolar);
    }


    private PessoaResponse montarRespostaFrontEnd(PessoaRequest pessoa, InformacoesImc imc, int anoNascimento, String impostoRenda, String validaMundial, String saldoDolar) {
        PessoaResponse response = new PessoaResponse();

        response.setNome(pessoa.getNome());
        response.setSobrenome(pessoa.getSobrenome());
        response.setSalario(impostoRenda);
        response.setAnoNascimento(anoNascimento);
        response.setMundialClubes(validaMundial);
        response.setSaldoEmDolar(saldoDolar);
        response.setImc(imc.getImc());
        response.setClassificacaoIMC(imc.getClassificacao());
        response.setTime(pessoa.getTime());
        response.setEndereco(pessoa.getEndereco());
        response.setAltura(pessoa.getAltura());
        response.setPeso(pessoa.getPeso());
        response.setSaldo(pessoa.getSaldo());
        response.setIdade(pessoa.getIdade());

        return response;
    }
}
