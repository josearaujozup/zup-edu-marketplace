package br.com.zup.edu.marketplace.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "usuarioClient",
        url = "http://localhost:9090/"
)
public interface UsuarioClient {

    @GetMapping("/usuarios/{id}")
    public Optional<DetalhaUsuarioResponse> detalhaUsuario(@PathVariable Long id);

}
