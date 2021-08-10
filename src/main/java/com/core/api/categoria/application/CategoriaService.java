package com.core.api.categoria.application;

import com.core.api.categoria.application.commands.CriarCategoriaCommand;
import com.core.api.categoria.domain.CategoriaRepository;
import com.core.api.security.service.exceptions.DataIntegrityException;
import com.core.api.security.service.exceptions.ObjectNotFoundException;
import com.core.api.categoria.application.commands.AlterarCategoriaCommand;
import com.core.api.categoria.application.commands.RemoverCategoriaCommand;
import com.core.api.categoria.domain.Categoria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService
{
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(UUID id)
	{
		Optional<Categoria> obj = repo.findById(id);

		//Se obj == null lanca exception
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria handle(CriarCategoriaCommand cmd)
	{
		var categoria = new Categoria();
		BeanUtils.copyProperties(cmd, categoria);
		return repo.save(categoria);
	}

	public Categoria handle(AlterarCategoriaCommand cmd)
	{
		var categoria = this.find(cmd.getId());
		categoria.setNome(cmd.getNome());
		return repo.save(categoria);
	}

	public void handle(RemoverCategoriaCommand cmd)
	{
		var categoriaId = cmd.getId();
		find(categoriaId);
		try
		{
			repo.deleteById(categoriaId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll()
	{
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}
