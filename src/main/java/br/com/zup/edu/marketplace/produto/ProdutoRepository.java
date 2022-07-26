package br.com.zup.edu.marketplace.produto;

import br.com.zup.edu.marketplace.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
