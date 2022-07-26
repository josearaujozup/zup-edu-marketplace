package br.com.zup.edu.marketplace.topico.Mensagem;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Mensagem {

    private UUID codigoPedido;

    private Comprador comprador;

    private List<Item> itens;

    private PagamentoMensagem pagamento;

    public Mensagem() {
    }

    public Mensagem(UUID codigoPedido, Comprador comprador, List<Item> itens, PagamentoMensagem pagamento) {
        this.codigoPedido = codigoPedido;
        this.comprador = comprador;
        this.itens = itens;
        this.pagamento = pagamento;
    }

    @Override
    public String toString() {
        return "{" +
                "codigoPedido=" + codigoPedido +
                ", comprador=" + comprador +
                ", itens=" + itens.stream().map(i -> i.toString()).collect(Collectors.toList()) +
                ", pagamento=" + pagamento +
                '}';
    }
}
