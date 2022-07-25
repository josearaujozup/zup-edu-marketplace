package br.com.zup.edu.marketplace.compra;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID id_pagamento;

    private String titular;

    private String numero;

    private YearMonth validoAte;

    private String codigoSeguranca;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ANALISANDO;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Forma forma = Forma.CARTAO_DE_CREDITO;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Pagamento() {
    }

    public Pagamento(UUID id_pagamento, String titular, String numero, YearMonth validoAte, String codigoSeguranca, BigDecimal valorTotal) {
        this.id_pagamento = id_pagamento;
        this.titular = titular;
        this.numero = numero;
        this.validoAte = validoAte;
        this.codigoSeguranca = codigoSeguranca;
        this.valorTotal = valorTotal;
    }

    public Pagamento(String titular, String numero, YearMonth validoAte, String codigoSeguranca, BigDecimal valorTotal) {
        this.titular = titular;
        this.numero = numero;
        this.validoAte = validoAte;
        this.codigoSeguranca = codigoSeguranca;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public UUID getId_pagamento() {
        return id_pagamento;
    }

    public String getTitular() {
        return titular;
    }

    public String getNumero() {
        return numero;
    }

    public YearMonth getValidoAte() {
        return validoAte;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public Status getStatus() {
        return status;
    }

    public Forma getForma() {
        return forma;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void mudarStatus(Status status) {
        this.status = status;
    }

    public void setId_pagamento(UUID id_pagamento) {
        this.id_pagamento = id_pagamento;
    }
}
