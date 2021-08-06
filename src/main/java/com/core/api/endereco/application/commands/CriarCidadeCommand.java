package com.core.api.endereco.application.commands;

import com.core.api.endereco.domain.Estado;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriarCidadeCommand {
    private String nome;
    private Estado estado;
}
