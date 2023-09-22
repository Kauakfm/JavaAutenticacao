package com.br.var.solutions.adapaters.input.Controllers;

import com.br.var.solutions.adapaters.input.Entities.PessoaRequest;
import com.br.var.solutions.adapaters.input.Entities.PessoaResponse;
import com.br.var.solutions.application.services.useCase.*;
import com.br.var.solutions.application.services.entities.InformacoesImc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pessoa")
@CrossOrigin(origins = "*")
@Slf4j
public class PessoaController {

    @Autowired
    private MundialUseCase mundialUseCase;
    @Autowired
    private ImcUseCase imcUseCase;
    @Autowired
    private AnoNascimentoUseCase anoNascimentoUseCase;
    @Autowired
    private ImpostoRendaUseCase impostoRendaUseCase;
    @Autowired
    private SaldoDolarUseCase saldoDolarUseCase;
    @Autowired
    private RespostaFrontEndUseCase respostaFrontEndUseCase;


    @GetMapping
    public ResponseEntity<List<PessoaResponse>> get() throws SQLException {
      List<PessoaResponse> listaDePessoas = respostaFrontEndUseCase.buscaListaPessoas();
      if(Objects.isNull(listaDePessoas)){
          return (ResponseEntity<List<PessoaResponse>>) ResponseEntity.notFound();
      }
        return ResponseEntity.ok(listaDePessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable int id) throws SQLException {
       PessoaResponse detalhesPessoa = respostaFrontEndUseCase.buscaDetalhesPessoa(id);
        if(Objects.isNull(detalhesPessoa)){
            return (ResponseEntity<PessoaResponse>)ResponseEntity.notFound();
        }
        return ResponseEntity.ok(detalhesPessoa);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaResponse> deletePessoa(@PathVariable int id) throws SQLException {
        respostaFrontEndUseCase.excluirDadosPessoa(id);
// esse string so ta ai para não retornar null no front e não da coco la "ERROR";
        String sucesso = "Dados excluidos com sucesso";
        return ResponseEntity.status(HttpStatus.OK).body(PessoaResponse.builder().build());
    }

    @PostMapping("/resumo")
    public ResponseEntity<PessoaResponse> getpessoa(@RequestBody PessoaRequest pessoinha, @RequestParam(value = "valida_mundial") Boolean DesejavalidarMundial) throws SQLException {
        InformacoesImc imc = InformacoesImc.builder().build();
        int anoNascimento = 0;
        double impostoRenda = 0;
        String validaMundial = null;
        double saldoDolar = 0;

        if (!pessoinha.getNome().isEmpty()) {
            log.info("Iniciando o processo de resumo da pessoa", pessoinha);
            if (Objects.nonNull(pessoinha.getPeso()) && Objects.nonNull(pessoinha.getAltura())) {
                log.info("Iniciando calculo de imc");
                imc = imcUseCase.calculoImc(pessoinha.getPeso(), pessoinha.getAltura());
            }
            if (Objects.nonNull(pessoinha.getIdade())) {
                log.info("Calculo de data Nascimento");
                anoNascimento = anoNascimentoUseCase.calculoAnoNasc(pessoinha.getIdade());
            }
            if (Objects.nonNull((pessoinha.getSalario()))) {
                log.info("Iniciando calculo de imposto de renda");
                impostoRenda = impostoRendaUseCase.calculoImpostoRenda(pessoinha.getSalario());
            }
            if (Boolean.TRUE.equals(DesejavalidarMundial)) {
                if (Objects.nonNull((pessoinha.getTime()))) {
                    log.info("Iniciando caluclo de calcula mundial");
                    validaMundial = mundialUseCase.calculoMundial(pessoinha.getTime());
                }
            }
            if (Objects.nonNull((pessoinha.getSaldo()))) {
                log.info("Iniciando calculo de saldo em dolar");
                saldoDolar = saldoDolarUseCase.convertSaldoDolar(pessoinha.getSaldo());
            }

            log.info("Montando o objeto de retorno para o front-end");
            PessoaResponse resumo = respostaFrontEndUseCase.mapper(pessoinha, imc, anoNascimento, impostoRenda, validaMundial, saldoDolar);

            return ResponseEntity.ok(resumo);
        }
        return ResponseEntity.noContent().build();
    }

}