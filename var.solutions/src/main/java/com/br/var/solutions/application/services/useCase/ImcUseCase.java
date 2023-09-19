package com.br.var.solutions.application.services.useCase;

import com.br.var.solutions.domain.entities.InformacoesImc;

public interface ImcUseCase {
    InformacoesImc calculoImc(double peso, double altura);
}
