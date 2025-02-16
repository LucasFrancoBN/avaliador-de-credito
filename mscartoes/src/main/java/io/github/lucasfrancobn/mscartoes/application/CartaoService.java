package io.github.lucasfrancobn.mscartoes.application;

import io.github.lucasfrancobn.mscartoes.domain.Cartao;
import io.github.lucasfrancobn.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository repository;

    @Transactional
    public Cartao salvar(Cartao cartao) {
        return repository.save(cartao);
    }

    @Transactional(readOnly = true)
    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        BigDecimal rendaBigDecimal = new BigDecimal(renda);

        return repository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
