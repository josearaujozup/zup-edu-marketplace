package br.com.zup.edu.marketplace.compra;

import javax.persistence.*;
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
    private Status status = Status.EM_ANALISE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Forma forma = Forma.CARTAO_CREDITO;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Pagamento() {
    }

    public Pagamento(UUID id_pagamento, String titular, String numero, YearMonth validoAte, String codigoSeguranca) {
        this.id_pagamento = id_pagamento;
        this.titular = titular;
        this.numero = numero;
        this.validoAte = validoAte;
        this.codigoSeguranca = codigoSeguranca;
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
}
