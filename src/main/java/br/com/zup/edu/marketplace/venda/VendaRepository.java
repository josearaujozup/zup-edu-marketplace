package br.com.zup.edu.marketplace.venda;

import br.com.zup.edu.marketplace.pagamento.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, UUID> {
}
