package com.infoeste.cursoSpringBoot.port.in;

import com.infoeste.cursoSpringBoot.domain.Usuario;

public interface BuscaUsuarioPorEmailUseCase {

    Usuario executa(String email);

}
