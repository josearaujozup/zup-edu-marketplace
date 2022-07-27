package br.com.zup.edu.marketplace.venda;

import br.com.zup.edu.marketplace.exception.ErroPadronizado;
import br.com.zup.edu.marketplace.pagamento.PagamentoRepository;
import br.com.zup.edu.marketplace.pagamento.Status;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoClient;
import br.com.zup.edu.marketplace.pagamento.client.PagamentoResponseClient;
import br.com.zup.edu.marketplace.produto.ProdutoRepository;
import br.com.zup.edu.marketplace.produto.client.DetalhaProdutoResponse;
import br.com.zup.edu.marketplace.produto.client.ProdutoClient;
import br.com.zup.edu.marketplace.usuario.client.DetalhaUsuarioResponse;
import br.com.zup.edu.marketplace.usuario.client.UsuarioClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @MockBean
    private UsuarioClient usuarioClient;

    @MockBean
    private ProdutoClient produtoClient;

    @MockBean
    private PagamentoClient pagamentoClient;

    @BeforeEach
    void setUp() {
        vendaRepository.deleteAll();
        produtoRepository.deleteAll();
        pagamentoRepository.deleteAll();
    }

    @Test
    void naoDeveCadastrarUmaVendaComDadosNulos() throws Exception {
        // Cenário

        PagamentoRequest pagamentoRequest = new PagamentoRequest(null, null, null, null);

        ProdutoRequest produtoRequest1 = new ProdutoRequest(null, null);
        ProdutoRequest produtoRequest2 = new ProdutoRequest(null, null);

        VendaRequest vendaRequest = new VendaRequest(null, List.of(produtoRequest1, produtoRequest2), pagamentoRequest);

        String payloadRequest = objectMapper.writeValueAsString(vendaRequest);

        MockHttpServletRequestBuilder request = post("/api/vendas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(payloadResponse, ErroPadronizado.class);

        assertThat(erroPadronizado.getMensagens())
                .hasSize(9)
                .contains("produtos[0].quantidade: não deve ser nulo",
                        "pagamento.numero: não deve estar em branco",
                        "pagamento.validoAte: não deve ser nulo",
                        "pagamento.codigoSeguranca: não deve estar em branco",
                        "produtos[1].quantidade: não deve ser nulo",
                        "pagamento.titular: não deve estar em branco",
                        "produtos[0].id: não deve ser nulo",
                        "usuario: não deve ser nulo",
                        "produtos[1].id: não deve ser nulo"
                );
    }


    @Test
    void naoDeveCadastrarUmaVendaComDadosInvalidos() throws Exception {
        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                "Jose Humberto Coimbra Jr",
                "54786598321545825",
                YearMonth.of(2020,05), "4299");

        ProdutoRequest produtoRequest1 = new ProdutoRequest(-4L, -4);
        ProdutoRequest produtoRequest2 = new ProdutoRequest(0L, 0);

        VendaRequest vendaRequest = new VendaRequest(-4L, List.of(produtoRequest1, produtoRequest2), pagamentoRequest);

        String payloadRequest = objectMapper.writeValueAsString(vendaRequest);

        MockHttpServletRequestBuilder request = post("/api/vendas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(payloadResponse, ErroPadronizado.class);

        assertThat(erroPadronizado.getMensagens())
                .hasSize(8)
                .contains("produtos[0].id: deve ser maior que 0",
                        "pagamento.numero: Deve conter 16 numeros",
                        "pagamento.validoAte: deve ser uma data futura",
                        "produtos[0].quantidade: deve ser maior que 0",
                        "pagamento.codigoSeguranca: Deve conter 3 numeros",
                        "usuario: deve ser maior que 0",
                        "produtos[1].quantidade: deve ser maior que 0",
                        "produtos[1].id: deve ser maior que 0"
                );
    }


    @Test
    void naoDeveCadastrarUmaVendaComUsuarioQueNaoExiste() throws Exception {

        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                "Jose Humberto Coimbra Jr",
                "5478659832154582",
                YearMonth.of(2023,05), "429");

        ProdutoRequest produtoRequest1 = new ProdutoRequest(1L, 1);
        ProdutoRequest produtoRequest2 = new ProdutoRequest(2L, 2);

        VendaRequest vendaRequest = new VendaRequest(200L, List.of(produtoRequest1, produtoRequest2), pagamentoRequest);

        when(usuarioClient.detalhaUsuario(any())).thenReturn(Optional.empty());

        String payloadRequest = objectMapper.writeValueAsString(vendaRequest);

        MockHttpServletRequestBuilder request = post("/api/vendas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isNotFound()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(payloadResponse, ErroPadronizado.class);

        assertThat(erroPadronizado.getMensagens())
                .hasSize(1)
                .contains("Usuario não encontrado");

    }

    @Test
    void naoDeveCadastrarUmaVendaComProdutoQueNaoExiste() throws Exception {

        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                "Jose Humberto Coimbra Jr",
                "5478659832154582",
                YearMonth.of(2023,05), "429");

        ProdutoRequest produtoRequest1 = new ProdutoRequest(20000L, 1);

        VendaRequest vendaRequest = new VendaRequest(1L, List.of(produtoRequest1), pagamentoRequest);

        DetalhaUsuarioResponse detalhaUsuarioResponse = new DetalhaUsuarioResponse(
                "Denes",
                "84768844006",
                "denes@email.com",
                "Rua",
                LocalDate.of(1997, 05, 05));

        when(usuarioClient.detalhaUsuario(any())).thenReturn(Optional.of(detalhaUsuarioResponse));
        when(produtoClient.detalhaProduto(any())).thenReturn(Optional.empty());

        String payloadRequest = objectMapper.writeValueAsString(vendaRequest);

        MockHttpServletRequestBuilder request = post("/api/vendas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isNotFound()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(payloadResponse, ErroPadronizado.class);

        assertThat(erroPadronizado.getMensagens())
                .hasSize(1)
                .contains("Produto não encontrado");

    }


    @Test
    void deveCadastrarUmaVenda() throws Exception {

        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                "Jose Humberto Coimbra Jr",
                "5478659832154582",
                YearMonth.of(2023,05), "429");

        ProdutoRequest produtoRequest1 = new ProdutoRequest(1L, 1);

        VendaRequest vendaRequest = new VendaRequest(1L, List.of(produtoRequest1), pagamentoRequest);

        DetalhaUsuarioResponse detalhaUsuarioResponse = new DetalhaUsuarioResponse(
                "Denes",
                "84768844006",
                "denes@email.com",
                "Rua",
                LocalDate.of(1997, 05, 05));

        DetalhaProdutoResponse detalhaProdutoResponse = new DetalhaProdutoResponse(
                1L,
                "Playstation 1",
                new BigDecimal("1000"),
                LocalDateTime.now());

        PagamentoResponseClient pagamentoResponseClient = new PagamentoResponseClient(
                UUID.randomUUID(),
                Status.APROVADO);

        when(usuarioClient.detalhaUsuario(any())).thenReturn(Optional.of(detalhaUsuarioResponse));
        when(produtoClient.detalhaProduto(any())).thenReturn(Optional.of(detalhaProdutoResponse));
        when(pagamentoClient.verificarPagamento(any())).thenReturn(pagamentoResponseClient);


        String payloadRequest = objectMapper.writeValueAsString(vendaRequest);

        MockHttpServletRequestBuilder request = post("/api/vendas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isCreated()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        List<Venda> vendas = vendaRepository.findAll();
        assertEquals(1, vendas.size());
    }

}