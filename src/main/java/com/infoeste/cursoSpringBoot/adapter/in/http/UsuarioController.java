package com.infoeste.cursoSpringBoot.adapter.in.http;

import com.infoeste.cursoSpringBoot.adapter.in.payload.UsuarioRequest;
import com.infoeste.cursoSpringBoot.adapter.in.payload.UsuarioResponse;
import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.in.BuscaUsuarioPorEmailUseCase;
import com.infoeste.cursoSpringBoot.port.in.BuscaUsuarioPorIdUseCase;
import com.infoeste.cursoSpringBoot.port.in.CadastraUsuarioUseCase;
import com.infoeste.cursoSpringBoot.port.in.ExcluiUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    //@Autowired
    //private CadastraUsuarioUseCase cadastraUsuario;

    private final CadastraUsuarioUseCase cadastraUsuario; // se for autowired, n pode ser final
    private final BuscaUsuarioPorIdUseCase buscaUsuarioPorId;
    private final BuscaUsuarioPorEmailUseCase buscaUsuarioPorEmail;
    private final ExcluiUsuarioPorIdUseCase excluiUsuarioPorId;

    /*
    public UsuarioController(CadastraUsuarioUseCase cadastraUsuario) {
        this.cadastraUsuario = cadastraUsuario;
    }
    */

    @PostMapping("/v1/usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@RequestBody UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setNome(request.getNome());

        Usuario resposta = cadastraUsuario.executa(usuario);

        UsuarioResponse payload = new UsuarioResponse();
        payload.setId(resposta.getId());
        payload.setEmail(resposta.getEmail());
        payload.setNome(resposta.getNome());
        return payload;
    }

    @GetMapping("/v1/usuario/{id}")
    public UsuarioResponse buscaPorId(@PathVariable UUID id) {
        Usuario usuario = buscaUsuarioPorId.executa(id);

        UsuarioResponse payload = new UsuarioResponse();
        payload.setId(usuario.getId());
        payload.setEmail(usuario.getEmail());
        payload.setNome(usuario.getNome());

        return payload;
    }

    @GetMapping("/v1/usuario/email/{email}")
    public UsuarioResponse buscaPorEmail(@PathVariable String email) {
        Usuario usuario = buscaUsuarioPorEmail.executa(email);

        UsuarioResponse payload = new UsuarioResponse();
        payload.setId(usuario.getId());
        payload.setEmail(usuario.getEmail());
        payload.setNome(usuario.getNome());

        return payload;
    }

    @DeleteMapping("/v1/usuario/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluiPorId(@PathVariable UUID id) {
        excluiUsuarioPorId.executar(id);
    }
}
