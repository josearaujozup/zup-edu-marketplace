package br.com.zup.edu.marketplace.pagamentoclient;

import br.com.zup.edu.marketplace.compra.Pagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.YearMonth;

public class PagamentoRequestClient {

    @NotBlank
    private String titular;

    @NotBlank
    @Pattern(regexp="[\\d]{16}", message = "Deve conter 16 numeros")
    private String numero;

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth validoAte;


    @NotBlank
    @Pattern(regexp="[\\d]{3}", message = "Deve conter 3 numeros")
    private String codigoSeguranca;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    public PagamentoRequestClient() {
    }

    public PagamentoRequestClient(String titular, String numero, YearMonth validoAte, String codigoSeguranca, BigDecimal valorTotal) {
        this.titular = titular;
        this.numero = numero;
        this.validoAte = validoAte;
        this.codigoSeguranca = codigoSeguranca;
        this.valorTotal = valorTotal;
    }

    public PagamentoRequestClient(Pagamento pagamento) {
        this.titular = pagamento.getTitular();
        this.numero = pagamento.getNumero();
        this.validoAte = pagamento.getValidoAte();
        this.codigoSeguranca = pagamento.getCodigoSeguranca();
        this.valorTotal = pagamento.getValorTotal();
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
