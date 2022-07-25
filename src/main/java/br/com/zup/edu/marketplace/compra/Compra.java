package br.com.zup.edu.marketplace.compra;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuario;

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

    public Compra(Long usuario, List<Produto> produtos, BigDecimal valorTotal) {
        this.usuario = usuario;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuario() {
        return usuario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
