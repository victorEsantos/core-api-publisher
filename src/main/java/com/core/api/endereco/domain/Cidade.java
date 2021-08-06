package com.core.api.endereco.domain;

import com.core.api.endereco.application.commands.AlterarCidadeCommand;
import com.core.api.endereco.application.commands.CriarCidadeCommand;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cidade implements Serializable
{

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private String nome;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	public static Cidade from(CriarCidadeCommand cmd) {
		return Cidade.builder()
				.nome(cmd.getNome())
				.estado(cmd.getEstado())
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

		Cidade cidade = (Cidade) o;

		return id != null ? id.equals(cidade.id) : cidade.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	public void alterar(AlterarCidadeCommand cmd) {
		this.nome = cmd.getNome();
		this.estado = cmd.getEstado();
	}
}
