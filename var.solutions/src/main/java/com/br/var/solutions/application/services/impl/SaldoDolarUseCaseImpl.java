package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.application.services.useCase.SaldoDolarUseCase;
import org.springframework.stereotype.Service;

@Service
public class SaldoDolarUseCaseImpl implements SaldoDolarUseCase {

public double convertSaldoDolar(double saldo)
{
    return converterSaldoEmDolar(saldo);
}


    private double converterSaldoEmDolar(double saldo) {
        return saldo / 5.11;
    }
}
