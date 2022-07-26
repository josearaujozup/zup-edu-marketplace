package br.com.zup.edu.marketplace.venda;

import br.com.zup.edu.marketplace.pagamento.client.PagamentoClient;
import br.com.zup.edu.marketplace.produto.client.ProdutoClient;
import br.com.zup.edu.marketplace.topico.Comprador;
import br.com.zup.edu.marketplace.topico.Item;
import br.com.zup.edu.marketplace.topico.Mensagem;
import br.com.zup.edu.marketplace.topico.PagamentoMensagem;
import br.com.zup.edu.marketplace.usuario.client.DetalhaUsuarioResponse;
import br.com.zup.edu.marketplace.usuario.client.UsuarioClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class VendaController {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProdutoClient produtoClient;

    @Autowired
    private PagamentoClient pagamentoClient;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    private final VendaRepository vendaRepository;

    public VendaController(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }


    @PostMapping("/api/vendas")
    @Transactional
    public ResponseEntity<?> comprar(@RequestBody @Valid VendaRequest request, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        System.out.println("Passou pela validação: " + request.getPagamento().getValidoAte());

        DetalhaUsuarioResponse usuarioResponse = usuarioClient.detalhaUsuario(request.getUsuario())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario não encontrado"));;

        Venda venda = request.toModel(produtoClient,pagamentoClient);
        vendaRepository.save(venda);

        Mensagem mensagem = prepararMensagemTopico(venda, usuarioResponse);
        String mensagemPayload = mapper.writeValueAsString(mensagem);
        kafkaTemplate.send("teste",mensagemPayload);

        URI location = uriComponentsBuilder.path("/api/vendas/{id}")
                .buildAndExpand(venda.getId())
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    private Mensagem prepararMensagemTopico(Venda venda, DetalhaUsuarioResponse usuarioResponse){
        Comprador comprador = new Comprador(usuarioResponse);

        List<Item> itens = new ArrayList<>();
        System.out.println("AQUIIIIII  Size Produtos: " + venda.getProdutos().size());
        venda.getProdutos().forEach(p ->{
            Item item = new Item(p.getProdutoId(), p.getNome(),p.getQuantidade(),p.getPreco());
            System.out.println("Nome do  Produto: "+p.getNome());
            itens.add(item);
        });

        PagamentoMensagem pagamentoMensagem = new PagamentoMensagem(
                venda.getPagamento().getId_pagamento(),
                venda.getPagamento().getForma(),
                venda.getPagamento().getStatus());

        Mensagem mensagem = new Mensagem(venda.getId(), comprador, itens, pagamentoMensagem);
        return mensagem;
    }


}
