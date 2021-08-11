package com.core.api.endereco.application.commands;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AlterarEnderecoCommand {
    private UUID id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private UUID clienteId;
    private UUID cidadeId;
}
