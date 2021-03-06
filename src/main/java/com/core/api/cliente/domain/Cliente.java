package com.core.api.cliente.domain;

import com.core.api.cliente.application.commands.AlterarClienteCommand;
import com.core.api.cliente.domain.enums.Perfil;
import com.core.api.cliente.domain.enums.TipoCliente;
import com.core.api.endereco.domain.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.core.api.pedido.domain.Pedido;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
public class Cliente implements Serializable
{
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private UUID id;
	private String nome;

	@Column(unique = true)
	private String email;
	private String cpfOuCnpj;

	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	@JsonIgnore
	private String senha;

	//JsonManagedReference para com referencia ciclica, porem dava alguns problemas com json e foi usado apenas um @JsonIgnore no backReference
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	//@JsonBackReference
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();

	public Cliente()
	{
		addPerfil(Perfil.CLIENTE);
	}

	@Builder
	public Cliente(UUID id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente, String senha)
	{
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipoCliente = tipoCliente;
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}


	public TipoCliente getTipoCliente()
	{
		return this.tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente)
	{
		this.tipoCliente = tipoCliente;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCode());
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

		Cliente cliente = (Cliente) o;

		return id != null ? id.equals(cliente.id) : cliente.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	public void alterar(AlterarClienteCommand cmd) {
		this.nome = cmd.getNome();
		this.email = cmd.getEmail();
	}
}
