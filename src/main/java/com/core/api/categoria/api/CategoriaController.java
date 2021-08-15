package com.core.api.categoria.api;

import com.core.api.categoria.application.commands.CriarCategoriaCommand;
import com.core.api.categoria.exception.CriarCategoriaConstraintException;
import com.core.api.categoria.api.dto.AlterarCategoriaDTO;
import com.core.api.categoria.api.dto.CriarCategoriaDTO;
import com.core.api.categoria.application.commands.AlterarCategoriaCommand;
import com.core.api.categoria.application.commands.RemoverCategoriaCommand;
import com.core.api.categoria.domain.Categoria;
import com.core.api.categoria.application.CategoriaService;
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
@RequestMapping(value = "/categorias")
@Api(value = "categoria controller")
@CrossOrigin(origins = "*")
public class CategoriaController
{
	@Autowired
	private CategoriaService service;

	@Autowired
	private ValidateService validator;

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Obtem categoria pelo ID")
	public ResponseEntity<Categoria> find(@PathVariable String id)
	{
		Categoria obj = service.find(UUID.fromString(id));
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	@ApiOperation(value = "Cria nova categoria")
	public ResponseEntity<Void> insert(@Valid @RequestBody CriarCategoriaDTO dto)
	{

		validator.validate(dto).ifPresent(violations -> {
			throw new CriarCategoriaConstraintException(violations);
		});

		var cmd = CriarCategoriaCommand
				.builder()
				.nome(dto.getNome())
				.build();
		var obj = service.handle(cmd);


		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Atualiza categoria")
	public ResponseEntity<Void> update(@Valid @RequestBody AlterarCategoriaDTO dto, @PathVariable Integer id)
	{

		validator.validate(dto).ifPresent(violations -> {
			throw new CriarCategoriaConstraintException(violations);
		});

		var cmd = AlterarCategoriaCommand
				.builder()
				.id(UUID.fromString(dto.getId()))
				.nome(dto.getNome())
				.build();
		service.handle(cmd);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deleta categoria pelo ID")
	public ResponseEntity<Void> delete(@PathVariable String id)
	{
		var cmd = RemoverCategoriaCommand.of(UUID.fromString(id));
		service.handle(cmd);

		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@ApiOperation(value = "Obtem todas as categorias")
	public ResponseEntity<List<CriarCategoriaDTO>> findAll()
	{
		List<Categoria> objList = service.findAll();
		List<CriarCategoriaDTO> dtoObjList = objList.stream().map(obj -> new CriarCategoriaDTO(obj))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(dtoObjList);
	}

	@GetMapping(value = "/page")
	@ApiOperation(value = "Obtem categorias filtradas")
	public ResponseEntity<Page<CriarCategoriaDTO>> findPage(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		Page<Categoria> objList = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CriarCategoriaDTO> dtoObjList = objList.map(obj -> new CriarCategoriaDTO(obj));

		return ResponseEntity.ok().body(dtoObjList);
	}

}
