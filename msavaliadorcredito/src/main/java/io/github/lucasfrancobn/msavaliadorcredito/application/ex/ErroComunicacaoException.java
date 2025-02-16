package io.github.lucasfrancobn.msavaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicacaoException extends RuntimeException {
    @Getter
    private Integer status;

    public ErroComunicacaoException(String message, Integer status) {
        super(message);
        status = status;
    }
}
