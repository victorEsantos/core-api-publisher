package com.core.api.cliente.api.dto;

import com.core.api.cliente.domain.Cliente;
import com.core.api.security.service.validation.ClienteUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
@Data
@NoArgsConstructor
public class ClienteDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 80, message = "deve ter entre 5 e 80 caracteres")
	private String name;
	@NotEmpty
	@Email(message = "email invalido")
	private String email;

	public ClienteDTO(Cliente cliente)
	{
		this.id = cliente.getId();
		this.name = cliente.getNome();
		this.email = cliente.getEmail();
	}
}
