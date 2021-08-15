package com.core.api.produto.api;

import com.core.api.util.URL;
import com.core.api.produto.api.dto.ProdutoDTO;
import com.core.api.produto.domain.Produto;
import com.core.api.produto.application.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/produtos")
@Api(value = "produto controller")
@CrossOrigin(origins = "*")
public class ProdutoController
{
	@Autowired
	private ProdutoService produtoService;

	//@GetMapping
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Obtem produto pelo ID")
	public ResponseEntity<Produto> find(@PathVariable String id)
	{
		Produto obj = produtoService.buscar(UUID.fromString(id));

		return ResponseEntity.ok().body(obj);
	}

	//para alterar o endPoint bastar colocar um "value = "/page" " dentro de requestMapping
	@GetMapping
	@ApiOperation(value = "Obtem produtos filtrados")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
		@RequestParam(value = "nome", defaultValue = "") String nome,
		@RequestParam(value = "categorias", defaultValue = "") String categorias,
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		String decodedName = URL.decodeString(nome);
		List<UUID> ids = URL.decodeStringList(categorias);

		Page<Produto> objList = produtoService.search(decodedName, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> dtoObjList = objList.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(dtoObjList);
	}
}
