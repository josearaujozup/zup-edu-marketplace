package br.com.zup.edu.marketplace.pagamento;

import br.com.zup.edu.marketplace.compra.Pagamento;
import br.com.zup.edu.marketplace.compra.PagamentoRepository;
import br.com.zup.edu.marketplace.compra.Status;
import br.com.zup.edu.marketplace.pagamentoclient.PagamentoClient;
import br.com.zup.edu.marketplace.pagamentoclient.PagamentoRequestClient;
import br.com.zup.edu.marketplace.pagamentoclient.PagamentoResponseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class pagamentoService {

    @Autowired
    private PagamentoClient pagamentoClient;

    private final PagamentoRepository pagamentoRepository;

    public pagamentoService(PagamentoRepository pagamentoRepository) {
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


    @Transactional
    @Scheduled(fixedRate = 3000)
    public void enviarPedidoFila(){
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
