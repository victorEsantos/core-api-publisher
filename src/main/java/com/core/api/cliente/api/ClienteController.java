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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
@Api(value = "cliente controller")
@CrossOrigin(origins = "*")
public class ClienteController
{
	@Autowired
	private ClienteService service;

	@Autowired
	private ValidateService validator;

	@PostMapping
	@ApiOperation(value = "Obtem categoria pelo ID")
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
				.cidadeId(UUID.fromString(dto.getCidadeId()))
				.build();

		var obj = service.handle(cmd);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	//@GetMapping
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Obtem cliente pelo ID")
	public ResponseEntity<Cliente> find(@PathVariable String id)
	{
		Cliente obj = service.find(UUID.fromString(id));

		return ResponseEntity.ok().body(obj);
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Atualiza dados do cliente")
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
				.cidadeId(UUID.fromString(dto.getCidadeId()))
				.build();

		service.handle(cmd);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deleta cliente pelo ID")
	public ResponseEntity<Void> delete(@PathVariable String id)
	{
		var cmd = RemoverClienteCommand.of(UUID.fromString(id));
		service.handle(cmd);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	@ApiOperation(value = "Obtem todos os clientes")
	public ResponseEntity<List<ClienteDTO>> findAll()
	{
		List<Cliente> objList = service.findAll();
		List<ClienteDTO> dtoObjList = objList.stream().map(obj -> new ClienteDTO(obj))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(dtoObjList);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	@ApiOperation(value = "Obtem clientes filtrados")
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
