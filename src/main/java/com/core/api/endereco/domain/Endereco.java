package com.core.api.endereco.domain;

import com.core.api.cliente.domain.Cliente;
import com.core.api.endereco.application.commands.AlterarEnderecoCommand;
import com.core.api.endereco.application.commands.CriarEnderecoCommand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco implements Serializable
{
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private UUID id;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;

	//@JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;

	public static Endereco from(CriarEnderecoCommand cmd) {
		return Endereco.builder()
				.logradouro(cmd.getLogradouro())
				.numero(cmd.getNumero())
				.complemento(cmd.getComplemento())
				.bairro(cmd.getBairro())
				.cep(cmd.getCep())
				.cliente(Cliente.builder().id(cmd.getClienteId()).build())
				.cidade(Cidade.builder().id(cmd.getCidadeId()).build())
				.build();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		Endereco endereco = (Endereco) o;

		return id != null ? id.equals(endereco.id) : endereco.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	public void alterar(AlterarEnderecoCommand cmd) {
		this.logradouro = cmd.getLogradouro();
		this.numero = cmd.getNumero();
		this.complemento = cmd.getComplemento();
		this.bairro = cmd.getBairro();
		this.cep = cmd.getCep();
	}
}
