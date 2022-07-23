package br.com.zup.edu.marketplace.compra;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

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
}
