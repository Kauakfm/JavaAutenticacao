package com.br.var.solutions;

import com.br.var.solutions.security.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/pessoa")
@Slf4j
public class PessoaController {

    private static  final String SECRET_KEY = "kauaferreiracwpoifnrepdINWCPCNncwcwkcwcnpwe";
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Object> get() {
        PessoaRequest pessoaRequest1 = new PessoaRequest();
        pessoaRequest1.setNome("Kaua");
        pessoaRequest1.setSobrenome("Ferreira");
        pessoaRequest1.setEndereco("Rua antonio carlos");
        pessoaRequest1.setIdade(17);

        return ResponseEntity.ok(pessoaRequest1);
    }

    @GetMapping("/resumo")
    public ResponseEntity<PessoaResponse> getpessoa(@RequestBody PessoaRequest pessoinha, @RequestParam(value = "valida_mundial") Boolean DesejavalidarMundial) {
        InformacoesImc imc = new InformacoesImc();
        int anoNascimento = 0;
        String impostoRenda = null;
        String validaMundial = null;
        String saldoDolar = null;

        if (!pessoinha.getNome().isEmpty()) {
            log.info("Iniciando o processo de resumo da pessoa", pessoinha);
            if (Objects.nonNull(pessoinha.getPeso()) && Objects.nonNull(pessoinha.getAltura())) {
                log.info("Iniciando calculo de imc");
                imc = calculaImc(pessoinha.getPeso(), pessoinha.getAltura());
            }
            if (Objects.nonNull(pessoinha.getIdade())) {
                log.info("Calculo de data Nascimento");
                anoNascimento = calculaAnoNascimento(pessoinha.getIdade());
            }
            if (Objects.nonNull((pessoinha.getSalario()))) {
                log.info("Iniciando calculo de imposto de renda");
                impostoRenda = calculaFaixaImpostoRenda(pessoinha.getSalario());
            }
           if(Boolean.TRUE.equals(DesejavalidarMundial)) {
               if (Objects.nonNull((pessoinha.getTime()))) {
                   log.info("Iniciando caluclo de calcula mundial");
                   validaMundial = calculaMundial(pessoinha.getTime());
               }
           }
            if (Objects.nonNull((pessoinha.getSaldo()))) {
                log.info("Iniciando calculo de saldo em dolar");
                saldoDolar = converterSaldoEmDolar(pessoinha.getSaldo());
            }

            log.info("Montando o objeto de retorno para o front-end");
            PessoaResponse resumo = montarRespostaFrontEnd(pessoinha,imc, anoNascimento, impostoRenda, validaMundial, saldoDolar);

            return ResponseEntity.ok(resumo);
        }
        return ResponseEntity.noContent().build();
    }

    private String converterSaldoEmDolar(double saldo) {
        return String.valueOf(saldo / 5.11);
    }

    private PessoaResponse montarRespostaFrontEnd(PessoaRequest pessoa,InformacoesImc imc, int anoNascimento, String impostoRenda, String validaMundial, String saldoDolar) {
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

          return response;
    }

    private String calculaMundial(String time) {
       if(time.equalsIgnoreCase("Corinthians")){
           return "Parabens, o seu time possui 2 mundiais de clubes conforme a FIFA";
        }
        else if(time.equalsIgnoreCase("São Paulo"))
       {
           return "Parabens, o seu time possui 3 mundiais de clubes confore a FIFA";
       }
        else if(time.equalsIgnoreCase("Santos"))
       {
           return "Parabens, o seu time possui 2 mundiais clubes conform a FIFA";
       }
        else {
            return "Que pena seu time não tem mundis";
       }
    }

    private String calculaFaixaImpostoRenda(double salario) {
        log.info("Iniciando o calculo do imposto de renda: " + salario);
        String novoSalarioCalculado;
        if (salario < 1903.98) {
            return "isento";
        } else if (salario > 1903.98 && salario < 2826.65) {
            double calculoIRF = 142.80 - ((salario * 0.075) / 100);
            double novoSalario = salario - calculoIRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;
        } else if (salario >= 2826.66 && salario < 375.05) {
            double calculoIRF = 354.80 - ((salario * 0.015) / 100);
            double novoSalario = salario - calculoIRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;
        } else if (salario >= 3751.06 && salario < 4664.68) {
            double calculoIRF = 636.13 - ((salario * 0.225) / 100);
            double novoSalario = salario - calculoIRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;
        } else {
            double calculoIRF = 869.36 - ((salario * 0.225) / 100);
            double novoSalario = salario - calculoIRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;
        }
    }

    private int calculaAnoNascimento(int idade) {
        LocalDate datalocal = LocalDate.now();
        int anoAtual = datalocal.getYear();
        return anoAtual - idade;
    }

    private InformacoesImc calculaImc(double peso, double altura) {
        double imc = peso / (altura * altura);

        InformacoesImc imcCalculado = new InformacoesImc();

        if (imc <= 18.5) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("abaixo do peso");
           return imcCalculado;
        } else if (imc > 18.5 && imc <= 24.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("peso ideal");
            return imcCalculado;
        } else if (imc > 24.9 && imc <= 29.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("exesso de peso");
            return imcCalculado;
        } else if (imc > 29.9 && imc <= 34.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("obesidade classe I");
            return imcCalculado;
        } else if (imc > 34.9 && imc < 39.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("obesidade classe II");
            return imcCalculado;
        } else {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("obesidade clase III");
            return imcCalculado;
        }
    }

    @PostMapping(path = "/authorization", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<GenerateToken> authorization (@RequestParam("client_id") String client_id,
                                                 @RequestParam("password") String password)
    {
        log.info("Bora tentar ne gerar o token se der certo blz se não der paciência");
     Boolean validaUsuario = ValidaUsuario.validaUsuario(client_id,password);

     if(Boolean.FALSE.equals(validaUsuario))
     {
         log.info("Seu token não vai ser gerado simplesmente pq não quero e .");
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GenerateToken());
     }

     long expirationDate = System.currentTimeMillis() + 1800000;

     String token = Jwts.builder()
             .setSubject(client_id)
             .setExpiration(new Date(expirationDate))
             .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
             .compact();

     GenerateToken tokenResponse = new GenerateToken();

        tokenResponse.setToken(token);
        tokenResponse.setExpiraEm(new Date(expirationDate));
        tokenResponse.setTempoValidacao(expirationDate);
        tokenResponse.setSolicitante(client_id);

        log.info("Seu token ta gerado pelo user :" + client_id + " em" + System.currentTimeMillis());
     return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping(path = "/authenticate", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<GenerateToken> generateToken (@RequestParam("client_id") String client_id,
                                                        @RequestParam("password") String password)
    {
        log.info("Bora tentar ne gerar o token se der certo blz se não der paciência");
        Boolean validaUsuario = ValidaUsuario.validaUsuario(client_id,password);

        if(Boolean.FALSE.equals(validaUsuario))
        {
            log.info("Seu token não vai ser gerado simplesmente pq não quero e .");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GenerateToken());
        }
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

       String token = jwtTokenUtil.generateToken(client_id);


        GenerateToken tokenResponse = new GenerateToken();
           tokenResponse.setToken(token);

        log.info("Seu token ta gerado pelo user :" + client_id + " em" + System.currentTimeMillis());
        return ResponseEntity.ok(tokenResponse);
    }

}
