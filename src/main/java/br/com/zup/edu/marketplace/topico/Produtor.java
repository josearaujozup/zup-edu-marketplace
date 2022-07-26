package br.com.zup.edu.marketplace.topico;

import br.com.zup.edu.marketplace.compra.Compra;
import br.com.zup.edu.marketplace.compra.CompraRepository;
import br.com.zup.edu.marketplace.compra.UsuarioResponse;
import br.com.zup.edu.marketplace.pagamento.Status;
import br.com.zup.edu.marketplace.topico.Mensagem.Comprador;
import br.com.zup.edu.marketplace.topico.Mensagem.Item;
import br.com.zup.edu.marketplace.topico.Mensagem.Mensagem;
import br.com.zup.edu.marketplace.topico.Mensagem.PagamentoMensagem;
import br.com.zup.edu.marketplace.usuario.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class Produtor {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    private final CompraRepository compraRepository;

    public Produtor(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void enviarMensagem(){

        System.out.println("Testando o job de envio de mensagem: ");

        List<Compra> compras = compraRepository.findTop3ByPagamentoStatusOrderByIdAsc(Status.APROVADO);
        System.out.println("AQUIIIIII  Size Compras: " + compras.size());


        compras.forEach(c -> {

            Comprador comprador = new Comprador(usuarioClient.detalhaUsuario(c.getUsuario()));

            List<Item> itens = new ArrayList<>();
            c.getProdutos().forEach(p ->{
                Item item = new Item(p.getProdutoId(), p.getNome(),p.getQuantidade(),p.getPreco());
                itens.add(item);
            });

            PagamentoMensagem pagamentoMensagem = new PagamentoMensagem(
                    c.getPagamento().getId_pagamento(),
                    c.getPagamento().getForma(),
                    c.getPagamento().getStatus());

            Mensagem mensagem = new Mensagem(c.getId(), comprador, itens, pagamentoMensagem);
            kafkaTemplate.send("teste3",mensagem.toString());
        });
    }

}
