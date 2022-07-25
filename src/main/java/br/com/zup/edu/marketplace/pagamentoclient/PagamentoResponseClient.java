package br.com.zup.edu.marketplace.pagamentoclient;

import br.com.zup.edu.marketplace.compra.Status;

import java.util.UUID;

public class PagamentoResponseClient {

    private UUID id;

    private Status status;

    public PagamentoResponseClient() {
    }

    public PagamentoResponseClient(UUID id, Status status) {
        this.id = id;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }
}
