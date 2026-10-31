package com.infoeste.cursoSpringBoot.application.usecase;

import com.infoeste.cursoSpringBoot.application.services.UsuarioLookup;
import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.in.ExcluiUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcluiUsuarioPorIdService implements ExcluiUsuarioPorIdUseCase {

    private final UsuarioLookup lookup;

    @Override
    public void executar(UUID id) {
        Optional<Usuario> encontrado = lookup.findById(id);

        if(encontrado.isEmpty())
            return;

        Usuario usuario = encontrado.get();
        usuario.setAtivo(false);
        lookup.save(usuario);
    }

}
