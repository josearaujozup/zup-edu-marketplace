package br.com.zup.edu.marketplace.topico;

import java.math.BigDecimal;

public class Item {

    private Long id;

    private String nome;

    private Integer quantidade;

    private BigDecimal valor;

    public Item(){

    }

    public Item(Long id, String nome, Integer quantidade, BigDecimal valor) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
