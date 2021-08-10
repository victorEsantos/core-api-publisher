package com.core.api.produto.api.dto;

import com.core.api.produto.domain.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private Double preco;

	public ProdutoDTO(Produto produto)
	{
		this.id = produto.getId().toString();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
	}
}
