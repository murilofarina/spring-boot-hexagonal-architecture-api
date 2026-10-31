package com.infoeste.cursoSpringBoot.application.usecase;

import com.infoeste.cursoSpringBoot.application.services.UsuarioLookup;
import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.in.CadastraUsuarioUseCase;
import com.infoeste.cursoSpringBoot.port.out.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CadastraUsuarioService implements CadastraUsuarioUseCase {

    private final UsuarioRepositorio repositorio;
    private final UsuarioLookup lookup;

    @Override
    public Usuario executa(Usuario usuario) {

        var encontrado = repositorio
                .findByEmail(usuario.getEmail());

        if(encontrado.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Email ja cadastrado"
            );
        }

        Usuario novo = new Usuario();

        novo.setNome(usuario.getNome());
        novo.setEmail(usuario.getEmail());
        novo.setId(UUID.randomUUID());
        novo.setAtivo(true);

        Usuario usuarioSalvo = lookup.save(novo);

        return usuarioSalvo;
    }

}