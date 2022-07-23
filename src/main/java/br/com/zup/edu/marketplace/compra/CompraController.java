package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.produtoclient.DetalhaProdutoResponse;
import br.com.zup.edu.marketplace.produtoclient.ProdutoClient;
import br.com.zup.edu.marketplace.usuarioclient.DetalhaUsuarioResponse;
import br.com.zup.edu.marketplace.usuarioclient.UsuarioClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class CompraController {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProdutoClient produtoClient;

    @GetMapping("/api/usuarios/{id}")
    public ResponseEntity<?> detalhaUsuario(@PathVariable Long id){

        DetalhaUsuarioResponse detalhaUsuarioResponse = null;
        try {
            detalhaUsuarioResponse = usuarioClient.detalhaUsuario(id);
        } catch (FeignException e) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario não encontrado");
        }

        return ResponseEntity.ok(detalhaUsuarioResponse);
    }

    @GetMapping("/api/produtos/{id}")
    public ResponseEntity<?> detalhaProduto(@PathVariable Long id){

        DetalhaProdutoResponse detalhaProdutoResponse = null;
        try {
            detalhaProdutoResponse = produtoClient.detalhaProduto(id);
        } catch (FeignException e) {
            throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado");
        }

        return ResponseEntity.ok(detalhaProdutoResponse);
    }


}
