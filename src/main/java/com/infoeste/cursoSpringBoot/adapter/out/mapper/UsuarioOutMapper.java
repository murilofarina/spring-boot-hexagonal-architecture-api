package com.infoeste.cursoSpringBoot.adapter.out.mapper;

import com.infoeste.cursoSpringBoot.adapter.out.UsuarioEntity;
import com.infoeste.cursoSpringBoot.domain.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioOutMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();

        entity.setId(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setAtivo(usuario.getAtivo());

        return entity;
    }

    public Usuario toUsuario(UsuarioEntity entity) {
        Usuario usuario = new Usuario();

        usuario.setId(entity.getId());
        usuario.setNome(entity.getNome());
        usuario.setEmail(entity.getEmail());
        usuario.setAtivo(entity.getAtivo());

        return usuario;
    }

}
