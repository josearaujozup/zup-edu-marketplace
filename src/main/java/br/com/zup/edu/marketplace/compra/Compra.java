package br.com.zup.edu.marketplace.compra;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario comprador;

    @OneToMany
    private List<Produto> produtos;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Compra() {
    }

    public Compra(Usuario comprador, List<Produto> produtos, BigDecimal valorTotal) {
        this.comprador = comprador;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
