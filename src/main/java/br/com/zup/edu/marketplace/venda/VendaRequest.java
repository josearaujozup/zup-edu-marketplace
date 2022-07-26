package br.com.zup.edu.marketplace.venda;

import br.com.zup.edu.marketplace.pagamento.Pagamento;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoClient;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoRequestClient;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoResponseClient;
import br.com.zup.edu.marketplace.produto.Produto;
import br.com.zup.edu.marketplace.produto.client.ProdutoClient;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class VendaRequest {

    @NotNull
    @Positive
    private Long usuario;

    @NotNull
    @Valid
    @Size(min = 1)
    private List<ProdutoRequest> produtos;

    @NotNull
    @Valid
    private PagamentoRequest pagamento;

    public VendaRequest(){

    }

    public VendaRequest(Long usuario, List<ProdutoRequest> produtos, PagamentoRequest pagamento) {
        this.usuario = usuario;
        this.produtos = produtos;
        this.pagamento = pagamento;
    }

    public Long getUsuario() {
        return usuario;
    }

    public List<ProdutoRequest> getProdutos() {
        return produtos;
    }

    public PagamentoRequest getPagamento() {
        return pagamento;
    }

    public Venda toModel(ProdutoClient produtoClient, PagamentoClient pagamentoClient) {

        List<Produto> listaProdutos = buscarProdutos(produtoClient);
        BigDecimal valorTotal = somarProdutos(listaProdutos);
        Pagamento entidadePagamento = new Pagamento(pagamento.getTitular(), pagamento.getNumero(), pagamento.getValidoAte(), pagamento.getCodigoSeguranca(),valorTotal);

        Pagamento pagamentoVerificado = verificarPagamento(entidadePagamento, pagamentoClient);

        Venda venda = new Venda(usuario, pagamentoVerificado);

        listaProdutos.forEach(produto -> {
            venda.adicionar(produto);
        });

//        return new Compra(usuario,listaProdutos,entidadePagamento);
        return venda;
    }
//

    private Pagamento verificarPagamento(Pagamento pagamento, PagamentoClient pagamentoClient){

        PagamentoRequestClient pagamentoRequestClient = new PagamentoRequestClient(pagamento);
        PagamentoResponseClient pagamentoResponseClient = pagamentoClient.verificarPagamento(pagamentoRequestClient);

        pagamento.mudarStatus(pagamentoResponseClient.getStatus());
        pagamento.setId_pagamento(pagamentoResponseClient.getId());

        return pagamento;
    }

    private List<Produto> buscarProdutos(ProdutoClient produtoClient){

        List<Produto> listaProdutos = new ArrayList<>();

        produtos.forEach(p -> {

            ProdutoResponse produto = new ProdutoResponse(produtoClient.detalhaProduto(p.getId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado")));

            listaProdutos.add(new Produto(produto.getId(),produto.getNome(),produto.getPreco(),p.getQuantidade()));
        });

        return listaProdutos;
    }

    private BigDecimal somarProdutos(List<Produto> produtos) {

        BigDecimal valorTotal = produtos.stream().map(p -> p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        return valorTotal;

    }
}
