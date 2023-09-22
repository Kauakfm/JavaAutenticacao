package com.br.var.solutions.application.services.impl;

import com.br.var.solutions.adapaters.input.Entities.Usuario;
import com.br.var.solutions.application.services.useCase.UsuarioUseCase;
import com.br.var.solutions.domain.entities.UsuarioEntity;
import com.br.var.solutions.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UsuarioUseCaseImpl implements UsuarioUseCase {

    @Autowired
    UsuarioRepository usuarioRepository;


    public Usuario buscarUsuario(Usuario user) {
        return getUsuario(user);
    }

    private Usuario getUsuario(Usuario user) {
        UsuarioEntity novoUsuario = usuarioRepository.findByUsuario(user.getUsuario());
        if (Objects.nonNull(novoUsuario) && novoUsuario.getSenha().equals(user.getSenha())) {
            return Usuario.builder()
                    .id(novoUsuario.getId())
                    .nome(novoUsuario.getNome())
                    .usuario(novoUsuario.getUsuario())
                    .senha(novoUsuario.getSenha())
                    .build();
        }
        return null;

    }


    public Usuario cadastraUsuario(Usuario user) {
        return cadastroUser(user);
    }

    private Usuario cadastroUser(Usuario user) {
        UsuarioEntity entity = new UsuarioEntity();

        entity.setUsuario(user.getUsuario());
        entity.setNome(user.getNome());
        entity.setSenha(user.getSenha());

        UsuarioEntity novoUsuario = usuarioRepository.save(entity);
        return Usuario.builder()
                .id(novoUsuario.getId())
                .nome(novoUsuario.getNome())
                .usuario(novoUsuario.getUsuario())
                .senha(novoUsuario.getSenha())
                .build();
    }

    public Usuario atualizarUsuario(Usuario user,Long id){
        return atualizaUser(user, id);
    }

    private Usuario atualizaUser(Usuario user, Long id) {
        Integer numId = (int) (long) id;
        //optional é pq ele pode ou não retornar um id
       Optional<UsuarioEntity> UsuarioExistente = Optional.ofNullable(usuarioRepository.findById(numId));

       if(UsuarioExistente.isPresent()){
           UsuarioEntity retornoExistente = UsuarioExistente.get();
           // so vai entrar aqui se o id realmente existir
           retornoExistente.setSenha(user.getSenha());
          UsuarioEntity usuarioAtualizado = usuarioRepository.save(retornoExistente);
        return Usuario.builder()
                  .id(usuarioAtualizado.getId())
                  .nome(usuarioAtualizado.getNome())
                  .usuario(usuarioAtualizado.getUsuario())
                  .senha(usuarioAtualizado.getSenha())
                  .build();
       }
        return null;
    }

}
