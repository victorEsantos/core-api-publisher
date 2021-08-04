package com.core.api.produto.api;

import com.core.api.util.URL;
import com.core.api.produto.api.dto.ProdutoDTO;
import com.core.api.produto.domain.Produto;
import com.core.api.produto.application.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController
{
	@Autowired
	private ProdutoService produtoService;

	//@GetMapping
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id)
	{
		Produto obj = produtoService.buscar(id);

		return ResponseEntity.ok().body(obj);
	}

	//para alterar o endPoint bastar colocar um "value = "/page" " dentro de requestMapping
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
		@RequestParam(value = "nome", defaultValue = "") String nome,
		@RequestParam(value = "categorias", defaultValue = "") String categorias,
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		String decodedName = URL.decodeString(nome);
		List<Integer> ids = URL.decodeIntList(categorias);

		Page<Produto> objList = produtoService.search(decodedName, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> dtoObjList = objList.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(dtoObjList);
	}
}
