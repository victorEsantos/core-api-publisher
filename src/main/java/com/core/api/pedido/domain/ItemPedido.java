package com.core.api.pedido.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.core.api.produto.domain.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ItemPedido implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double desconto;
	private Integer quantidade;
	private Double preco;

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco)
	{
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public double getSubTotal()
	{
		return (preco - desconto) * quantidade;
	}

	@JsonIgnore
	public Pedido getPedido()
	{
		return id.getPedido();
	}

	public void setPedido(Pedido pedido)
	{
		id.setPedido(pedido);
	}

	public Produto getProduto()
	{
		return id.getProduto();
	}

	public void setProduto(Produto produto)
	{
		id.setProduto(produto);
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

		ItemPedido that = (ItemPedido) o;

		return id != null ? id.equals(that.id) : that.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString()
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		StringBuilder sb = new StringBuilder();
		sb.append(getProduto().getNome());
		sb.append(", Qtde: ");
		sb.append(getQuantidade());
		sb.append(", preço unitario: ");
		sb.append(nf.format(getPreco()));
		sb.append(", SubTotal: ");
		sb.append(nf.format(getSubTotal()));
		sb.append("\n");

		return sb.toString();
	}
}
