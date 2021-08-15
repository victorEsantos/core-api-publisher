package com.core.api.pedido.api;

import com.core.api.pedido.domain.Pedido;
import com.core.api.pedido.application.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedidos")
@Api(value = "pedido controller")
@CrossOrigin(origins = "*")
public class PedidoController
{
	@Autowired
	private PedidoService service;

	//@GetMapping
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Obtem pedido pelo ID")
	public ResponseEntity<Pedido> find(@PathVariable String id)
	{
		Pedido obj = service.buscar(UUID.fromString(id));

		return ResponseEntity.ok().body(obj);
	}

	@Transactional
	@PostMapping
	@ApiOperation(value = "Insere novo pedido")
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	@ApiOperation(value = "Obtem pedidos filtrados")
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction)
	{
		Page<Pedido> objList = service.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(objList);
	}

}
