package com.br.var.solutions.domain.repositories;

import com.br.var.solutions.domain.entities.PessoaEntity;
import com.br.var.solutions.domain.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
    PessoaEntity findById (int id);

    void deleteById(int id);

}
