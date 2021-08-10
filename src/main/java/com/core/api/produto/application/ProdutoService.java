package com.core.api.produto.application;

import com.core.api.categoria.domain.CategoriaRepository;
import com.core.api.produto.domain.ProdutoRepository;
import com.core.api.security.service.exceptions.ObjectNotFoundException;
import com.core.api.categoria.domain.Categoria;
import com.core.api.produto.domain.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService
{
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscar(UUID id)
	{
		Optional<Produto> obj = produtoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String name, List<UUID> ids, Integer page, Integer linesPerPage,
								String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest
			.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.search(name, categorias, pageRequest);
	}
}
