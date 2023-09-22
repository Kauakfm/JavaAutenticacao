package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.application.services.entities.InformacoesImc;
import com.br.var.solutions.application.services.useCase.ImcUseCase;
import org.springframework.stereotype.Service;

@Service
public class ImcUseCaseImpl implements ImcUseCase {

public InformacoesImc calculoImc(double peso, double altura){
    return  calculaImc(peso, altura);
}


    private InformacoesImc calculaImc(double peso, double altura) {
        double imc = peso / (altura * altura);

        if (imc <= 18.5) {
            return InformacoesImc.builder()
                    .imc(imc)
                    .classificacao("abaixo do peso")
                    .build();
        } else if (imc > 18.5 && imc <= 24.9) {
            return InformacoesImc.builder()
                    .imc(imc)
                    .classificacao("peso ideal")
                    .build();
        } else if (imc > 24.9 && imc <= 29.9) {
            return InformacoesImc.builder()
                    .imc(imc)
                    .classificacao("exesso de peso")
                    .build();
        } else if (imc > 29.9 && imc <= 34.9) {
            return InformacoesImc.builder()
                    .imc(imc)
                    .classificacao("obesidade classe I")
                    .build();
        } else if (imc > 34.9 && imc < 39.9) {
            return InformacoesImc.builder()
                    .imc(imc)
                    .classificacao("obesidade classe II")
                    .build();
        } else {
            return InformacoesImc.builder()
                    .imc(imc)
                    .classificacao("obesidade classe III")
                    .build();
        }
    }




}
