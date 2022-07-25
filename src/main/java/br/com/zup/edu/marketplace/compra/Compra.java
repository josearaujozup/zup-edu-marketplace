package br.com.zup.edu.marketplace.compra;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Long usuario;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<Produto> produtos;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @OneToOne
    private Pagamento pagamento;

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

    public UUID getId() {
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
