package com.br.var.solutions.application.services.useCase;

import com.br.var.solutions.adapaters.input.Entities.Usuario;

public interface UsuarioUseCase {
    Usuario cadastraUsuario(Usuario user);

    Usuario buscarUsuario(Usuario user);

}
