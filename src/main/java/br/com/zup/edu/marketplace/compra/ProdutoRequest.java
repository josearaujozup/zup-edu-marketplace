package br.com.zup.edu.marketplace.compra;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProdutoRequest {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long quantidade;

    public ProdutoRequest(){

    }

    public ProdutoRequest(Long id, Long quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public Long getQuantidade() {
        return quantidade;
    }
}
