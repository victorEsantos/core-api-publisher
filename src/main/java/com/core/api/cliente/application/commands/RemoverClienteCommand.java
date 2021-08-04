package com.core.api.cliente.application.commands;

import lombok.Data;

@Data(staticConstructor = "of")
public class RemoverClienteCommand {
    private final Integer id;
}
