package br.com.zup.edu.marketplace.venda;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.YearMonth;

public class PagamentoRequest {

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

    public PagamentoRequest(){

    }

    public PagamentoRequest(String titular, String numero, YearMonth validoAte, String codigoSeguranca) {
        this.titular = titular;
        this.numero = numero;
        this.validoAte = validoAte;
        this.codigoSeguranca = codigoSeguranca;
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
}
