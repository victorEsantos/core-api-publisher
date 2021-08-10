package com.core.api.categoria.application.commands;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AlterarCategoriaCommand {
    private final UUID id;
    private final String nome;
}
