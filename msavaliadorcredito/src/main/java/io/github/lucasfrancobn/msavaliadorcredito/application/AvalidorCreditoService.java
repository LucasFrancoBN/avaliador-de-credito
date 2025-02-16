package io.github.lucasfrancobn.msavaliadorcredito.application;

import feign.FeignException;
import io.github.lucasfrancobn.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.lucasfrancobn.msavaliadorcredito.application.ex.ErroComunicacaoException;
import io.github.lucasfrancobn.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import io.github.lucasfrancobn.msavaliadorcredito.domain.model.*;
import io.github.lucasfrancobn.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.lucasfrancobn.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.github.lucasfrancobn.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvalidorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher publisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        try {
            // obterDadosCliente - MSCLIENTES
            // obter cartoes do cliente - MSCARTOES

            ResponseEntity<DadosCliente> response = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartaoResponse = cartoesClient.getCartoesByCliente(cpf);

            return new SituacaoCliente(response.getBody(), cartaoResponse.getBody());
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException("Cliente não encontrado");
            }
            throw new ErroComunicacaoException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao (String cpf, Long renda) {
        try {
            ResponseEntity<DadosCliente> response = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartaoResponse = cartoesClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartaoResponse.getBody();

            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = response.getBody();

                BigDecimal limiteBasico = cartao.limiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.idade());
                var fator = idadeBD.divide(BigDecimal.TEN);
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                return new CartaoAprovado(cartao.nome(), cartao.bandeira(), limiteAprovado);
            }).toList();

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException("Cliente não encontrado");
            }
            throw new ErroComunicacaoException(e.getMessage(), status);
        }
    }

    public Object solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
            publisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
