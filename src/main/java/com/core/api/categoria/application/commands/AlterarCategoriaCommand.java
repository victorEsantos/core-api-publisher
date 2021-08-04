package com.core.api.categoria.application.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlterarCategoriaCommand {
    private final Integer id;
    private final String nome;
}
