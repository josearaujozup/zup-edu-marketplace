package br.com.zup.edu.marketplace.usuarioclient;

import java.time.LocalDate;

public class DetalhaUsuarioResponse {

    private String nome;

    private String cpf;

    private String email;

    private String endereco;

    private LocalDate dataNascimento;

    public DetalhaUsuarioResponse(String nome, String cpf, String email, String endereco, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
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
