package com.infoeste.cursoSpringBoot.application.usecase;

import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.in.BuscaUsuarioPorEmailUseCase;
import com.infoeste.cursoSpringBoot.port.out.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//@RequiredArgsConstructor
@Service
public class BuscaUsuarioPorEmailService implements BuscaUsuarioPorEmailUseCase {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Override
    public Usuario executa(String email) {
        Usuario encontrado = repositorio.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ));

        if(!encontrado.getAtivo())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );

        return encontrado;
    }

    /*
    @Override
    public Usuario executa(String email) {

        Optional<Usuario> encontrado = repositorio.findByEmail(email);

        if(encontrado.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        return encontrado.get();
    }
    */
}
