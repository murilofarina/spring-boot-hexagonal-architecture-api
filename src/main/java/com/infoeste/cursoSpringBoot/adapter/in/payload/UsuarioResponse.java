package com.infoeste.cursoSpringBoot.adapter.in.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class UsuarioResponse {

    private UUID id;
    private String nome;
    private String email;

}
