package io.github.lucasfrancobn.msclientes.application;

import io.github.lucasfrancobn.msclientes.domain.Cliente;
import io.github.lucasfrancobn.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
