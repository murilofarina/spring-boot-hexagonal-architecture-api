package com.infoeste.cursoSpringBoot.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Usuario {

    private UUID id;
    private String nome;
    private String email;
    private Boolean ativo;

}
