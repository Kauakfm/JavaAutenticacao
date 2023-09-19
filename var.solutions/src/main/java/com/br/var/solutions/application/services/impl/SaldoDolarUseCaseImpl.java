package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.application.services.useCase.SaldoDolarUseCase;
import org.springframework.stereotype.Service;

@Service
public class SaldoDolarUseCaseImpl implements SaldoDolarUseCase {

public String convertSaldoDolar(double saldo)
{
    return converterSaldoEmDolar(saldo);
}


    private String converterSaldoEmDolar(double saldo) {
        return String.valueOf(saldo / 5.11);
    }
}
