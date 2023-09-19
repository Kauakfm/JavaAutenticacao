package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.application.services.useCase.MundialUseCase;
import org.springframework.stereotype.Service;

@Service
public class MundialUseCaseImpl implements MundialUseCase {

    public String calculoMundial(String time)
    {
        return  calculaMundial(time);
    }
    private String calculaMundial(String time) {
        if (time.equalsIgnoreCase("Corinthians")) {
            return "Parabens, o seu time possui 2 mundiais de clubes conforme a FIFA";
        } else if (time.equalsIgnoreCase("São Paulo")) {
            return "Parabens, o seu time possui 3 mundiais de clubes confore a FIFA";
        } else if (time.equalsIgnoreCase("Santos")) {
            return "Parabens, o seu time possui 2 mundiais clubes conform a FIFA";
        } else {
            return "Que pena seu time não tem mundis";
        }
    }

}
