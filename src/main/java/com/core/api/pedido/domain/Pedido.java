package com.core.api.pedido.domain;

import com.core.api.cliente.domain.Cliente;
import com.core.api.endereco.domain.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.core.api.pagamento.domain.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Pedido implements Serializable
{
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private UUID id;

	@JsonFormat(pattern = "dd/mm/yyyy HH:mm")
	private Date instante;

	//JsonManagedReference para com referencia ciclica, porem dava alguns problemas com json e foi usado apenas um @JsonIgnore no backReference
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;

	//JsonManagedReference para com referencia ciclica, porem dava alguns problemas com json e foi usado apenas um @JsonIgnore no backReference
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "endereco_entrega_id")
	private Endereco enderecoDeEntrega;

	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	public double getValorTotal()
	{
		double soma = 0.0;

		for(ItemPedido itemPedido : itens)
		{
			soma += itemPedido.getSubTotal();
		}
		return soma;
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

		Pedido pedido = (Pedido) o;

		return id != null ? id.equals(pedido.id) : pedido.id == null;
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		StringBuilder sb = new StringBuilder();
		sb.append("Pedido numero: ");
		sb.append(getId());
		sb.append(", Instante: ");
		sb.append(sdf.format(getInstante()));
		sb.append(", Cliente: ");
		sb.append(getCliente().getNome());
		sb.append(", Situação do Pagamento: ");
		sb.append(getPagamento().getEstadoPagamento().getDescription());
		sb.append("\nDetalhes\n");
		for(ItemPedido ip : getItens())
		{
			sb.append(ip.toString());
		}
		sb.append("Valor total: ");
		sb.append(nf.format(getValorTotal()));

		return sb.toString();
	}
}
