package br.com.zup.edu.marketplace.venda;

import br.com.zup.edu.marketplace.pagamento.Pagamento;
import br.com.zup.edu.marketplace.produto.Produto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuario;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="venda_id")
    private List<Produto> produtos = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Pagamento pagamento;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Venda() {
    }

    public Venda(Long usuario, Pagamento pagamento) {
        this.usuario = usuario;
        this.pagamento = pagamento;
    }

    public Venda(Long usuario, List<Produto> produtos, Pagamento pagamento) {
        this.usuario = usuario;
        this.produtos = produtos;
        this.pagamento = pagamento;
    }

    //    public UUID getId() {
//        return id;
//    }

    public Long getId() {
        return id;
    }

    public Long getUsuario() {
        return usuario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }


}
