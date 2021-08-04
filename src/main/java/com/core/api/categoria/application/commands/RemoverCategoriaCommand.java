package com.core.api.categoria.application.commands;

import lombok.Data;

@Data(staticConstructor = "of")
public class RemoverCategoriaCommand {
    private final Integer id;
}
