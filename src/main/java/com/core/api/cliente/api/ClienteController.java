package com.core.api.cliente.api;

import com.core.api.cliente.application.commands.AlterarClienteCommand;
import com.core.api.cliente.application.commands.CriarClienteCommand;
import com.core.api.cliente.api.dto.ClienteDTO;
import com.core.api.cliente.api.dto.CriarClienteDTO;
import com.core.api.cliente.application.commands.RemoverClienteCommand;
import com.core.api.cliente.domain.Cliente;
import com.core.api.cliente.application.ClienteService;
import com.core.api.cliente.exception.CriarClienteConstraintException;
import com.core.api.util.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController
{
	@Autowired
	private ClienteService service;

	@Autowired
	private ValidateService validator;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CriarClienteDTO dto)
	{

		validator.validate(dto).ifPresent(violations -> {
			throw new CriarClienteConstraintException(violations);
		});

		var cmd = CriarClienteCommand
				.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.cpfOuCnpj(dto.getCpfOuCnpj())
				.senha(dto.getSenha())
				.tipoCliente(dto.getTipoCliente())
				.logradouro(dto.getLogradouro())
				.numero(dto.getNumero())
				.complemento(dto.getComplemento())
				.bairro(dto.getBairro())
				.cep(dto.getCep())
				.telefone1(dto.getTelefone1())
				.cidadeId(dto.getCidadeId())
				.build();

		var obj = service.handle(cmd);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	//@GetMapping
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id)
	{
		Cliente obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CriarClienteDTO dto,
		@PathVariable Integer id)
	{

		var cmd = AlterarClienteCommand
				.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.cpfOuCnpj(dto.getCpfOuCnpj())
				.senha(dto.getSenha())
				.tipoCliente(dto.getTipoCliente())
				.logradouro(dto.getLogradouro())
				.numero(dto.getNumero())
				.complemento(dto.getComplemento())
				.bairro(dto.getBairro())
				.cep(dto.getCep())
				.telefone1(dto.getTelefone1())
				.cidadeId(dto.getCidadeId())
				.build();

		service.handle(cmd);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id)
	{
		var cmd = RemoverClienteCommand.of(id);
		service.handle(cmd);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll()
	{
		List<Cliente> objList = service.findAll();
		List<ClienteDTO> dtoObjList = objList.stream().map(obj -> new ClienteDTO(obj))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(dtoObjList);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		Page<Cliente> objList = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> dtoObjList = objList.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(dtoObjList);
	}
}
