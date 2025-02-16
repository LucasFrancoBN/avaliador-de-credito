package io.github.lucasfrancobn.msclientes.application;

import io.github.lucasfrancobn.msclientes.application.representation.ClienteSaveRequest;
import io.github.lucasfrancobn.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ClienteSaveRequest request) {
        Cliente cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> dadosCliente(@PathVariable String cpf) {
        Optional<Cliente> cliente = service.getByCpf(cpf);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public String status() {
        // Esse método foi implementado apenas para saber o status da aplicação,
        // se subiu corretamente ou não.
        return "OK";
    }
}
