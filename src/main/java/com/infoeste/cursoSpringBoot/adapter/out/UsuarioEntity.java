package com.infoeste.cursoSpringBoot.adapter.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity // hibernate e spring entendem que é uma entidade de banco de dados
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 300)
    private String nome;

    @Column(unique = true, nullable = false, length = 300)
    private String email;

    @Column(nullable = false)
    private Boolean ativo;

}
