package com.infoeste.cursoSpringBoot.port.in;

import com.infoeste.cursoSpringBoot.domain.Usuario;

import java.util.UUID;

public interface ExcluiUsuarioPorIdUseCase {

    void executar(UUID id);

}
