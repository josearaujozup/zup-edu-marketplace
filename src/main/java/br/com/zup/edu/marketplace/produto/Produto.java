package br.com.zup.edu.marketplace.produto;

import br.com.zup.edu.marketplace.venda.Venda;
import br.com.zup.edu.marketplace.venda.ProdutoRequest;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produtoId;

    private String nome;

    private BigDecimal preco;

    private Integer quantidade;


    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Produto() {
    }

    public Produto(Long produtoId, String nome, BigDecimal preco, Integer quantidade) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(ProdutoRequest produtoRequest) {
        this.produtoId = produtoRequest.getId();
        this.quantidade = produtoRequest.getQuantidade();
    }

    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
