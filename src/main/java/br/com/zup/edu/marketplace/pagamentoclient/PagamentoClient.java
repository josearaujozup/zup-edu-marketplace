package br.com.zup.edu.marketplace.pagamentoclient;

import br.com.zup.edu.marketplace.compra.PagamentoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
@FeignClient(
        name = "pagamentoClient",
        url = "http://localhost:8081/"
)
public interface PagamentoClient {

    @PostMapping("/pagamentos/credito")
    public PagamentoResponseClient verificarPagamento(@RequestBody @Valid PagamentoRequestClient request);

}
