package com.infoeste.cursoSpringBoot.application.usecase;

import com.infoeste.cursoSpringBoot.application.services.UsuarioLookup;
import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.in.BuscaUsuarioPorIdUseCase;
import com.infoeste.cursoSpringBoot.port.out.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscaUsuarioPorIdService implements BuscaUsuarioPorIdUseCase {

    @Autowired
    private UsuarioLookup repositorio;

    @Override
    public Usuario executa(UUID id) {

        Optional<Usuario> encontrado = repositorio.findById(id);

        if(encontrado.isEmpty())
            throw new ResponseStatusException (
                    HttpStatus.NOT_FOUND
            );

        if(encontrado.get().getAtivo())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );

        return encontrado.get();
    }
}
