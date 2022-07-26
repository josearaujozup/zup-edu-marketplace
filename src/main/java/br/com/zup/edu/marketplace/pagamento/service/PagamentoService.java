package br.com.zup.edu.marketplace.pagamento.service;

import br.com.zup.edu.marketplace.pagamento.Pagamento;
import br.com.zup.edu.marketplace.pagamento.PagamentoRepository;
import br.com.zup.edu.marketplace.pagamento.Status;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoClient;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoRequestClient;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoResponseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class PagamentoService {

    @Autowired
    private PagamentoClient pagamentoClient;

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 3000)
    public void realizarPagamento(){
        System.out.println("Testando o job: ");

        List<Pagamento> pagamentos = pagamentoRepository.findTop3ByStatusOrderByIdAsc(Status.ANALISANDO);
//        System.out.println("0: " + pagamentos.get(0).getStatus());
        System.out.println("AQUIIIIII  Size: " + pagamentos.size());

        pagamentos.forEach(p -> {
            PagamentoRequestClient pagamentoRequestClient = new PagamentoRequestClient(p);
            PagamentoResponseClient pagamentoResponseClient = pagamentoClient.verificarPagamento(pagamentoRequestClient);

            p.mudarStatus(pagamentoResponseClient.getStatus());
            p.setId_pagamento(pagamentoResponseClient.getId());
        });
    }

}
