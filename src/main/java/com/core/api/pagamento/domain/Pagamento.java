package com.core.api.pagamento.domain;

import com.core.api.pagamento.domain.enums.EstadoPagamento;
import com.core.api.pedido.domain.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public abstract class Pagamento implements Serializable
{
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private UUID id;
	private Integer estadoPagamento;

	//@JsonBackReference
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;


	public EstadoPagamento getEstadoPagamento()
	{
		return EstadoPagamento.getSafeEstadoPagamento(estadoPagamento);
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento)
	{
		this.estadoPagamento = estadoPagamento.getCode();
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

		Pagamento pagamento = (Pagamento) o;

		return id != null ? id.equals(pagamento.id) : pagamento.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}
