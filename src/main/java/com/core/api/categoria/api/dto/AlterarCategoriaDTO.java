package com.core.api.categoria.api.dto;

import com.core.api.categoria.domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlterarCategoriaDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull
	private String id;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "o tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	public AlterarCategoriaDTO(Categoria categoria)
	{
		this.id = categoria.getId().toString();
		this.nome = categoria.getNome();
	}
}
