package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.produtoclient.ProdutoClient;
import feign.FeignException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    public Compra toModel(ProdutoClient produtoClient) {

        List<Produto> listaProdutos = buscarProdutos(produtoClient);
        BigDecimal valorTotal = somarProdutos(listaProdutos);
        Pagamento entidadePagamento = new Pagamento(pagamento.getTitular(), pagamento.getNumero(), pagamento.getValidoAte(), pagamento.getCodigoSeguranca(),valorTotal);

        return new Compra(usuario,listaProdutos,entidadePagamento);
    }
//

    private List<Produto> buscarProdutos(ProdutoClient produtoClient){

        List<Produto> listaProdutos = new ArrayList<>();

        produtos.forEach(p -> {

            ProdutoResponse produto = null;
            try {
                produto = new ProdutoResponse(produtoClient.detalhaProduto(p.getId()));
            } catch (FeignException e) {
                throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado");
            }

            listaProdutos.add(new Produto(produto.getId(),produto.getNome(),produto.getPreco(),p.getQuantidade()));
        });

        return listaProdutos;
    }

    private BigDecimal somarProdutos(List<Produto> produtos) {

        BigDecimal valorTotal = produtos.stream().map(p -> p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        return valorTotal;

    }
}
