package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.produto.client.DetalhaProdutoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoResponse {

    private Long id;

    private String nome;

    private BigDecimal preco;

    private LocalDateTime criadoEm;

    public ProdutoResponse() {
    }

    public ProdutoResponse(DetalhaProdutoResponse detalhaProdutoResponse) {
        this.id = detalhaProdutoResponse.getId();
        this.nome = detalhaProdutoResponse.getNome();
        this.preco = detalhaProdutoResponse.getPreco();
        this.criadoEm = detalhaProdutoResponse.getCriadoEm();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
