package com.core.api.pagamento.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComCartao")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class PagamentoComCartao extends Pagamento
{

	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;

	public void setNumeroDeParcelas(Integer numeroDeParcelas)
	{
		this.numeroDeParcelas = numeroDeParcelas;
	}
}
