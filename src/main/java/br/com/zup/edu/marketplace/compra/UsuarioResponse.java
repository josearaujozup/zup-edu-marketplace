package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.usuarioclient.DetalhaUsuarioResponse;

import java.time.LocalDate;

public class UsuarioResponse {

    private String nome;

    private String cpf;

    private String email;

    private String endereco;

    private LocalDate dataNascimento;

    public UsuarioResponse() {
    }

    public UsuarioResponse(DetalhaUsuarioResponse detalhaUsuarioResponse) {
        this.nome = detalhaUsuarioResponse.getNome();
        this.cpf = detalhaUsuarioResponse.getCpf();
        this.email = detalhaUsuarioResponse.getEmail();
        this.endereco = detalhaUsuarioResponse.getEndereco();
        this.dataNascimento = detalhaUsuarioResponse.getDataNascimento();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

}
