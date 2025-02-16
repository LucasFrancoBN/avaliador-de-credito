package io.github.lucasfrancobn.mscartoes.application;

import io.github.lucasfrancobn.mscartoes.domain.ClienteCartao;
import io.github.lucasfrancobn.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listarCartoesByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
