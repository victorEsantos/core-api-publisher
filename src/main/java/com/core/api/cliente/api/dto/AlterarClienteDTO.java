package com.core.api.cliente.api.dto;

import com.core.api.security.service.validation.ClienteInsert;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ClienteInsert
@Data
@NoArgsConstructor
public class AlterarClienteDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer id;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 80, message = "deve ter entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email(message = "email invalido")
	private String email;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String cpfOuCnpj;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String senha;

	private Integer tipoCliente;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String logradouro;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String telefone1;
	private String telefone2;
	private String telefone3;

	private Integer cidadeId;
}
