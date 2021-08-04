package com.core.api.cliente.domain.enums;

public enum TipoCliente
{
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int code;
	private String description;

	TipoCliente(int code, String description)
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

	public static TipoCliente getSafeTipoCliente(Integer code)
	{
		TipoCliente tipoCliente = null;

		if (code != null)
		{
			for (TipoCliente tc : TipoCliente.values())
			{
				if (code.equals(tc.getCode()))
				{
					tipoCliente = tc;
				}
			}

			if (tipoCliente.equals(null))
			{
				throw new IllegalArgumentException("Id inválido: " + code);
			}
		}

		return tipoCliente;
	}
}
