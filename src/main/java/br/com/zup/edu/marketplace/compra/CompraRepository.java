package br.com.zup.edu.marketplace.compra;

import br.com.zup.edu.marketplace.pagamento.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompraRepository extends JpaRepository<Compra, UUID> {
    List<Compra> findTop3ByPagamentoStatusOrderByIdAsc(Status status);
}
