package io.github.lucasfrancobn.msclientes.application.representation;

import io.github.lucasfrancobn.msclientes.domain.Cliente;
import lombok.Data;

public record ClienteSaveRequest (
    String cpf,
    String nome,
    Integer idade
) {
    public Cliente toModel() {
        return new Cliente(cpf(), nome(), idade());
    }
}
