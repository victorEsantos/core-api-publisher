package com.core.api.endereco.application.commands;

import com.core.api.endereco.domain.Estado;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AlterarCidadeCommand {
    private UUID id;
    private String nome;
    private Estado estado;
}
