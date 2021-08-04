package com.core.api.pagamento.domain.enums;

public enum EstadoPagamento
{
	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3,"Cancelado");

	private int code;
	private String description;

	EstadoPagamento(int code, String description)
	{
		this.code = code;
		this.description = description;
	}

	public int getCode()
	{
		return code;
	}


	public String getDescription()
	{
		return description;
	}

	public static EstadoPagamento getSafeEstadoPagamento(Integer code)
	{
		EstadoPagamento estadoPagamento = null;

		if (code != null)
		{
			for (EstadoPagamento ep : EstadoPagamento.values())
			{
				if (code.equals(ep.getCode()))
				{
					estadoPagamento = ep;
				}
			}

			if (estadoPagamento.equals(null))
			{
				throw new IllegalArgumentException("Id inválido: " + code);
			}
		}

		return estadoPagamento;
	}
}
