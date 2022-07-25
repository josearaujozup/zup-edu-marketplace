package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.produtoclient.ProdutoClient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CompraRequest {

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

    public CompraRequest(){

    }

    public CompraRequest(Long usuario, List<ProdutoRequest> produtos, PagamentoRequest pagamento) {
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

//    public Compra toModel(ProdutoClient produtoClient) {
//
//        BigDecimal ValorTotal = somarProdutos(produtoClient);
//
//        List<Produto> produtosLista = produtos.stream()
//                .map(Produto::new).collect(Collectors.toList());
//
//        return new Compra(usuario,produtosLista,);
//    }
//
    public BigDecimal somarProdutos(List<ProdutoResponse> produtos) {

        return produtos.stream().map(p -> p.getPreco()).reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
