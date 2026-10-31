package com.infoeste.cursoSpringBoot.application.services;

import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.out.UsuarioCache;
import com.infoeste.cursoSpringBoot.port.out.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioLookup {

    private final UsuarioCache cache;
    private final UsuarioRepositorio repositorio;

    public Usuario save(Usuario toSave) {
        Usuario usuarioSalvo = repositorio.save(toSave);
        cache.deleteById(toSave.getId());
        return usuarioSalvo;
    }

    //@Transactional
    public Optional<Usuario> findById(UUID id) {
        Optional<Usuario> usuarioCache = cache.findById(id);

        if(usuarioCache.isPresent())
            return usuarioCache;

        Optional<Usuario> usuarioBanco = repositorio.findById(id);

        if(usuarioBanco.isEmpty())
            return Optional.empty();

        cache.save(usuarioBanco.get());

        return usuarioBanco;
    }

}
