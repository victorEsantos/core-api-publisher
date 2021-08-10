package com.core.api.categoria.application.commands;

import lombok.Data;

import java.util.UUID;

@Data(staticConstructor = "of")
public class RemoverCategoriaCommand {
    private final UUID id;
}
