package br.com.zup.edu.marketplace.produtoclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "produtoClient",
        url = "http://localhost:8082/"
)
public interface ProdutoClient {

    @GetMapping("/produtos/{id}")
    public DetalhaProdutoResponse detalhaProduto(@PathVariable Long id);

}
