package com.core.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Email(message = "email invalido")
    private String email;
}
