package com.infoeste.cursoSpringBoot.port.out;

import com.infoeste.cursoSpringBoot.domain.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositorio {
    Usuario save(Usuario toSave);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(UUID id);
}
