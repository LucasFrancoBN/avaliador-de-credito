package io.github.lucasfrancobn.msavaliadorcredito.domain.model;

import java.util.List;

public record SituacaoCliente(
        DadosCliente cliente,
        List<CartaoCliente> cartoes
) {
}
