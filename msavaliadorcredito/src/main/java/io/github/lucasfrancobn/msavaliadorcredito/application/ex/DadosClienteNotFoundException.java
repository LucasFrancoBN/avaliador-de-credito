package io.github.lucasfrancobn.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundException extends RuntimeException {
    public DadosClienteNotFoundException(String message) {
        super(message);
    }
}
