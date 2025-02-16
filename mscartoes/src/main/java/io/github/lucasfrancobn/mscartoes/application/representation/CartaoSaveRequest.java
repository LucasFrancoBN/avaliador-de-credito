package io.github.lucasfrancobn.mscartoes.application.representation;


import io.github.lucasfrancobn.mscartoes.domain.BandeiraCartao;
import io.github.lucasfrancobn.mscartoes.domain.Cartao;

import java.math.BigDecimal;

public record CartaoSaveRequest (
        String nome,
        BandeiraCartao bandeira,
        BigDecimal renda,
        BigDecimal limite
) {
    public Cartao toCartao() {
        return new Cartao(nome(), bandeira(), renda(), limite());
    }
}
