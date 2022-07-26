package br.com.zup.edu.marketplace.topico;


import java.util.List;

public class Mensagem {

//    private UUID codigoPedido;
    private Long codigoPedido;

    private Comprador comprador;

    private List<Item> itens;

    private PagamentoMensagem pagamento;

    public Mensagem() {
    }

    public Mensagem(Long codigoPedido, Comprador comprador, List<Item> itens, PagamentoMensagem pagamento) {
        this.codigoPedido = codigoPedido;
        this.comprador = comprador;
        this.itens = itens;
        this.pagamento = pagamento;
    }

    public Long getCodigoPedido() {
        return codigoPedido;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public List<Item> getItens() {
        return itens;
    }

    public PagamentoMensagem getPagamento() {
        return pagamento;
    }

}
