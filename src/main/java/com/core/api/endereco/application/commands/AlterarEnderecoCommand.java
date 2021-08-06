package com.core.api.endereco.application.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlterarEnderecoCommand {
    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private Integer clienteId;
    private Integer cidadeId;
}
