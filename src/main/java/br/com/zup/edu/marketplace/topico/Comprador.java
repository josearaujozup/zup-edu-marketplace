package br.com.zup.edu.marketplace.topico;

import br.com.zup.edu.marketplace.usuario.client.DetalhaUsuarioResponse;

import java.time.LocalDate;

public class Comprador {

    private String nome;

    private String cpf;

    private String endereco;

    private String email;

    private LocalDate dataNascimento;

    public Comprador(){

    }

    public Comprador(DetalhaUsuarioResponse detalhaUsuarioResponse) {
        this.nome = detalhaUsuarioResponse.getNome();
        this.cpf = detalhaUsuarioResponse.getCpf();
        this.endereco = detalhaUsuarioResponse.getEndereco();
        this.email = detalhaUsuarioResponse.getEmail();
        this.dataNascimento = detalhaUsuarioResponse.getDataNascimento();
    }


    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

}
