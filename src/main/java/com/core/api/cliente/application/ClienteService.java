package com.core.api.cliente.application;

import com.core.api.cliente.application.commands.AlterarClienteCommand;
import com.core.api.cliente.application.commands.CriarClienteCommand;
import com.core.api.cliente.application.commands.RemoverClienteCommand;
import com.core.api.cliente.domain.ClienteRepository;
import com.core.api.endereco.domain.EnderecoRepository;
import com.core.api.security.service.exceptions.AuthorizationException;
import com.core.api.security.service.exceptions.DataIntegrityException;
import com.core.api.security.service.exceptions.ObjectNotFoundException;
import com.core.api.cliente.api.dto.ClienteDTO;
import com.core.api.cliente.api.dto.CriarClienteDTO;
import com.core.api.endereco.domain.Cidade;
import com.core.api.cliente.domain.Cliente;
import com.core.api.endereco.domain.Endereco;
import com.core.api.cliente.domain.enums.Perfil;
import com.core.api.cliente.domain.enums.TipoCliente;
import com.core.api.security.userss.UserSS;
import com.core.api.security.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService
{

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(UUID id)
	{

		UserSS user = UserService.authenticated();

		if(user == null || !user.hasRole(Perfil.ADMIN) && !user.getId().equals(id)){
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente handle(CriarClienteCommand cmd)
	{
		var cliente = new Cliente();
		BeanUtils.copyProperties(cmd, cliente);
		return repository.save(cliente);
	}

	public Cliente handle(AlterarClienteCommand cmd)
	{
		var cliente = this.find(cmd.getId());
		cliente.alterar(cmd);
		return repository.save(cliente);
	}

	public void handle(RemoverClienteCommand cmd)
	{
		var id = cmd.getId();
		find(id);
		try
		{
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

	public List<Cliente> findAll()
	{
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest
			.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente cliFromDTO(ClienteDTO clienteDTO)
	{
		return new Cliente(UUID.fromString(clienteDTO.getId()), clienteDTO.getName(), clienteDTO.getEmail(), null,
			null, null);
	}

	public Cliente cliFromDTO(CriarClienteDTO criarClienteDTO)
	{
		Cliente cli = new Cliente(null, criarClienteDTO.getNome(), criarClienteDTO.getEmail(),
			criarClienteDTO.getCpfOuCnpj(),
			TipoCliente.getSafeTipoCliente(criarClienteDTO.getTipoCliente()), pe.encode(criarClienteDTO.getSenha()));

		Cidade cidade = new Cidade(UUID.fromString(criarClienteDTO.getCidadeId()), null, null);

		Endereco end = new Endereco(null, criarClienteDTO.getLogradouro(), criarClienteDTO.getNumero(),
			criarClienteDTO.getComplemento(), criarClienteDTO.getBairro(), criarClienteDTO.getCep(), cli,
			cidade);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(criarClienteDTO.getTelefone1());

		if (criarClienteDTO.getTelefone2() != null)
		{
			cli.getTelefones().add(criarClienteDTO.getTelefone2());
		}

		if (criarClienteDTO.getTelefone3() != null)
		{
			cli.getTelefones().add(criarClienteDTO.getTelefone2());
		}

		return cli;
	}


}
