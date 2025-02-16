package io.github.lucasfrancobn.mscartoes.application;

import io.github.lucasfrancobn.mscartoes.application.representation.CartaoSaveRequest;
import io.github.lucasfrancobn.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.lucasfrancobn.mscartoes.domain.Cartao;
import io.github.lucasfrancobn.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toCartao();
        cartaoService.salvar(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam Long renda) {
        var list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>>getCartoesByCliente(@RequestParam String cpf) {
        List<ClienteCartao> list = clienteCartaoService.listarCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = list.stream().map(CartoesPorClienteResponse::fromModel).toList();

        return ResponseEntity.ok(resultList);
    }

    @GetMapping
    public String status() {
        return "OK";
    }
}
