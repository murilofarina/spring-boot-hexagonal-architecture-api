package com.infoeste.cursoSpringBoot.adapter.out;

import com.infoeste.cursoSpringBoot.domain.Usuario;
import com.infoeste.cursoSpringBoot.port.out.UsuarioCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioRedisCache implements UsuarioCache {

    private final RedisTemplate<String, String> redis;
    private final JsonMapper jsonMapper;

    @Value("${app.cache.time}")
    private Integer cacheTime;

    @Override
    public Usuario save(Usuario toSave) {
        String json = jsonMapper.writeValueAsString(toSave);

        redis.opsForValue().set(
                toSave.getId().toString(),
                json,
                Duration.ofSeconds(cacheTime)
        );

        return toSave;
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        String json = redis.opsForValue().get(id.toString());

        if(json == null)
            return Optional.empty();

        Usuario usuario =  jsonMapper
                .readValue(
                        json, Usuario.class
                );

        return Optional.of(usuario);
    }

    @Override
    public void deleteById(UUID id) {
        redis.delete(id.toString());
    }
}
