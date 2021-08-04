package com.core.api.endereco.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cidade implements Serializable
{

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private String nome;

	//JsonManagedReference para com referencia ciclica, porem dava alguns problemas com json e foi usado apenas um @JsonIgnore no backReference
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;

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
}
