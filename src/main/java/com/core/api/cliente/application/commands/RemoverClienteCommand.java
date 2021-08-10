package com.core.api.cliente.application.commands;

import lombok.Data;

import java.util.UUID;

@Data(staticConstructor = "of")
public class RemoverClienteCommand {
    private final UUID id;
}
