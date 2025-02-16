package io.github.lucasfrancobn.mscartoes.application.representation;

import io.github.lucasfrancobn.mscartoes.domain.BandeiraCartao;
import io.github.lucasfrancobn.mscartoes.domain.ClienteCartao;

import java.math.BigDecimal;

public record CartoesPorClienteResponse (
        String nome,
        BandeiraCartao bandeira,
        BigDecimal limiteLiberado
) {
    public static CartoesPorClienteResponse fromModel(ClienteCartao model) {
        return new CartoesPorClienteResponse(
                model.getCartao().getNome(),
                model.getCartao().getBandeira(),
                model.getLimite()
        );
    }
}
