package io.github.lucasfrancobn.msavaliadorcredito.domain.model;

import java.math.BigDecimal;

public record CartaoCliente(
        String nome,
        String bandeira,
        BigDecimal limiteLiberado
) {
}
