package io.github.lucasfrancobn.mscartoes.domain;

import java.math.BigDecimal;

public record DadosSolicitacaoEmissaoCartao(
        Long idCartao,
        String cpf,
        String endereco,
        BigDecimal limiteLiberado
) {
}