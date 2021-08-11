package com.core.api.cliente.application.commands;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AlterarClienteCommand {
    private final UUID id;
    private final String nome;
    private final String email;
    private final String cpfOuCnpj;
    private final String senha;
    private final Integer tipoCliente;
    private final String logradouro;
    private final String numero;
    private final String complemento;
    private final String bairro;
    private final String cep;
    private final String telefone1;
    private final UUID cidadeId;
}
