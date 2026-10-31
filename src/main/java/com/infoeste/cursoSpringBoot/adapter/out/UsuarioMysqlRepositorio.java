package com.infoeste.cursoSpringBoot.adapter.out;

import com.infoeste.cursoSpringBoot.adapter.out.mapper.UsuarioOutMapper;
import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.out.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository // spring trata como serviço
@RequiredArgsConstructor
public class UsuarioMysqlRepositorio implements UsuarioRepositorio {

    private final UsuarioJpaRepositorio repositorio;
    private final UsuarioOutMapper mapper;

    @Override
    public Usuario save(Usuario toSave) {
        var entity = mapper.toEntity(toSave);

        UsuarioEntity entidadeSalva = repositorio.save(entity);

        return mapper.toUsuario(entidadeSalva);
    }

    //@Override
    public Optional<Usuario> findByEmail(String email) {
        return repositorio.findByEmail(email)
                .map(mapper::toUsuario);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return repositorio.findById(id)
                .map(mapper::toUsuario);

    }

}
