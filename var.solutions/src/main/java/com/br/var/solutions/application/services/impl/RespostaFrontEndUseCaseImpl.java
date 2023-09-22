package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.application.services.entities.InformacoesImc;
import com.br.var.solutions.adapaters.input.Entities.PessoaRequest;
import com.br.var.solutions.adapaters.input.Entities.PessoaResponse;
import com.br.var.solutions.application.services.useCase.RespostaFrontEndUseCase;
import com.br.var.solutions.domain.entities.PessoaEntity;
import com.br.var.solutions.domain.entities.UsuarioEntity;
import com.br.var.solutions.domain.repositories.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class RespostaFrontEndUseCaseImpl implements RespostaFrontEndUseCase {

    @Autowired
    PessoaRepository repository;

    public PessoaResponse mapper(PessoaRequest pessoa, InformacoesImc imc, int anoNascimento, double impostoRenda, String validaMundial, double saldoDolar) throws SQLException {
        return montarRespostaFrontEnd(pessoa, imc, anoNascimento, impostoRenda, validaMundial, saldoDolar);
    }

    private PessoaResponse montarRespostaFrontEnd(PessoaRequest pessoa, InformacoesImc imc, int anoNascimento, double impostoRenda, String validaMundial, double saldoDolar) throws SQLException {

        try {
            PessoaResponse pessoaResponse = PessoaResponse
                    .builder()
                    .nome(pessoa.getNome())
                    .imc(imc.getImc())
                    .classificacaoIMC(imc.getClassificacao())
                    .salario(impostoRenda)
                    .anoNascimento(anoNascimento)
                    .mundialClubes(validaMundial)
                    .saldoEmDolar(saldoDolar)
                    .time(pessoa.getTime())
                    .endereco(pessoa.getEndereco())
                    .altura(pessoa.getAltura())
                    .peso(pessoa.getPeso())
                    .Saldo(pessoa.getSaldo())
                    .idade(pessoa.getIdade())
                    .saldoEmDolar(saldoDolar)
                    .build();

            PessoaEntity pessoaEntity = new PessoaEntity();
            pessoaEntity.setNome(pessoaResponse.getNome());
            pessoaEntity.setDtNascimento(LocalDate.now());
            pessoaEntity.setAltura(pessoaResponse.getAltura());
            pessoaEntity.setPeso(pessoaResponse.getPeso());
            pessoaEntity.setSalario(pessoaResponse.getSalario());
            pessoaEntity.setSaldo(pessoaResponse.getSaldo());
            pessoaEntity.setIdade(pessoaResponse.getIdade());
            pessoaEntity.setImc(pessoaResponse.getImc());
            pessoaEntity.setClassificacao(pessoaResponse.getClassificacaoIMC());
            pessoaEntity.setSalarioLiquido(pessoaResponse.getSalario());
            pessoaEntity.setSaldoDolar(saldoDolar);
            UsuarioEntity userEntity = new UsuarioEntity();
            userEntity.setId(pessoa.getUserId());
            pessoaEntity.setUsuario(userEntity);

            repository.save(pessoaEntity);
            log.info("Dados de pessoa salvo com sucesso");
            return pessoaResponse;
        } catch (Exception e) {
            throw new SQLException("Erro ao salvar no banco de dados", e);
        }
    }

    public List<PessoaResponse> buscaListaPessoas() throws SQLException {
        return listPessoas();
    }

    private List<PessoaResponse> listPessoas() throws SQLException {
        try {
            List<PessoaEntity> pessoasEntity = repository.findAll(); //pegar todos
            List<PessoaResponse> pessoasResponse = new ArrayList<>();

            pessoasEntity.stream().forEach(entity -> {
                PessoaResponse pessoaResponse = PessoaResponse.builder().build();
                pessoaResponse.setUserId(entity.getId());
                pessoaResponse.setNome(entity.getNome());
                pessoaResponse.setDtNascimento(entity.getDtNascimento());
                pessoaResponse.setAltura(entity.getAltura());
                pessoaResponse.setPeso(entity.getPeso());
                pessoaResponse.setSalario(entity.getSalario());
                pessoaResponse.setSaldo(entity.getSaldo());
                pessoaResponse.setIdade(entity.getIdade());
                pessoaResponse.setImc(entity.getImc());
                pessoaResponse.setClassificacaoIMC(entity.getClassificacao());
                pessoaResponse.setAliquota(entity.getAliquota());
                pessoaResponse.setSaldoEmDolar(entity.getSaldoDolar());

                pessoasResponse.add(pessoaResponse);
            });
            return pessoasResponse;
        } catch (Exception e) {
            throw new SQLException("Erro ao pegar us coiso do banco de dados.");
        }

    }

    public PessoaResponse buscaDetalhesPessoa(int id) throws SQLException {
        return buscaDetalhesDaPessoa(id);
    }

    private PessoaResponse buscaDetalhesDaPessoa(int id) throws SQLException {
        PessoaEntity pessoaEntity = repository.findById(id);
        try {
            return PessoaResponse.builder()
                    .UserId(pessoaEntity.getId())
                    .nome(pessoaEntity.getNome())
                    .dtNascimento(pessoaEntity.getDtNascimento())
                    .altura(pessoaEntity.getAltura())
                    .peso(pessoaEntity.getPeso())
                    .salario(pessoaEntity.getSalario())
                    .Saldo(pessoaEntity.getSaldo())
                    .idade(pessoaEntity.getIdade())
                    .imc(pessoaEntity.getImc())
                    .classificacaoIMC(pessoaEntity.getClassificacao())
                    .aliquota(pessoaEntity.getAliquota())
                    .salario(pessoaEntity.getSalario())
                    .saldoEmDolar(pessoaEntity.getSaldoDolar())
                    .build();
        } catch (Exception e) {
            throw new SQLException("Erro ao pegar os dados dos caras");
        }
    }

    public void excluirDadosPessoa(int id) throws SQLException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new SQLException("Erro ao excluir os dados dos cara");
        }

    }
}
