package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.produtoclient.DetalhaProdutoResponse;
import br.com.zup.edu.marketplace.produtoclient.ProdutoClient;
import br.com.zup.edu.marketplace.usuarioclient.DetalhaUsuarioResponse;
import br.com.zup.edu.marketplace.usuarioclient.UsuarioClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class CompraController {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProdutoClient produtoClient;

    private final CompraRepository compraRepository;

    public CompraController(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

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


    @PostMapping("/api/compras")
    @Transactional
    public ResponseEntity<?> comprar(@RequestBody @Valid CompraRequest request, UriComponentsBuilder uriComponentsBuilder){
        System.out.println("Passou pela validação: " + request.getPagamento().getValidoAte());

        UsuarioResponse usuarioResponse = null;
        try {
            usuarioResponse =  new UsuarioResponse(usuarioClient.detalhaUsuario(request.getUsuario()));
        } catch (FeignException e) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario não encontrado");
        }

        Compra compra = request.toModel(produtoClient);
        compraRepository.save(compra);

        System.out.println("Compra id: " + compra.getId());

//        ProdutoResponse produtoResponse = null;
//        try {
//            produtoResponse = new ProdutoResponse(produtoClient.detalhaProduto(request.getProdutos().get(0).getId()));
//        } catch (FeignException e) {
//            throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado");
//        }


        return ResponseEntity.ok(usuarioResponse);
    }


}
