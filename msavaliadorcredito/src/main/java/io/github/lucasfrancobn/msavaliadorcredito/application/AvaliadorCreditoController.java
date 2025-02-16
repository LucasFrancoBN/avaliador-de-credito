package io.github.lucasfrancobn.msavaliadorcredito.application;

import io.github.lucasfrancobn.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.lucasfrancobn.msavaliadorcredito.application.ex.ErroComunicacaoException;
import io.github.lucasfrancobn.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import io.github.lucasfrancobn.msavaliadorcredito.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvalidorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "OK";
    }

    @GetMapping(value = "situacao-cliente")
    public ResponseEntity<Object> consultaSituacaoCliente(@RequestParam String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);

            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.cpf(), dados.renda());

            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @PostMapping("solicitacao-cartao")
    public ResponseEntity<Object> solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = (ProtocoloSolicitacaoCartao) avaliadorCreditoService
                    .solicitarEmissaoCartao(dados);

            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (ErroSolicitacaoCartaoException ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
