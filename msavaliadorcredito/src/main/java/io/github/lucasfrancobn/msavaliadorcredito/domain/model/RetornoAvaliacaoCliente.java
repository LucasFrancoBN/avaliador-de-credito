package io.github.lucasfrancobn.msavaliadorcredito.domain.model;

import lombok.Data;

import java.util.List;

public record RetornoAvaliacaoCliente (
        List<CartaoAprovado> cartoes
) {
}
