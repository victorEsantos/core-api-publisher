package com.core.api.endereco.application;

import com.core.api.cliente.domain.enums.Perfil;
import com.core.api.endereco.application.commands.AlterarCidadeCommand;
import com.core.api.endereco.application.commands.AlterarEnderecoCommand;
import com.core.api.endereco.application.commands.CriarCidadeCommand;
import com.core.api.endereco.application.commands.CriarEnderecoCommand;
import com.core.api.endereco.domain.Cidade;
import com.core.api.endereco.domain.CidadeRepository;
import com.core.api.endereco.domain.Endereco;
import com.core.api.endereco.domain.EnderecoRepository;
import com.core.api.security.service.UserService;
import com.core.api.security.service.exceptions.AuthorizationException;
import com.core.api.security.service.exceptions.ObjectNotFoundException;
import com.core.api.security.userss.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnderecoService
{

	@Autowired
	private EnderecoRepository repository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public Endereco findEndereco(UUID id)
	{

		UserSS user = UserService.authenticated();

		var obj = repository.findById(id);

		if(user == null || !user.hasRole(Perfil.ADMIN) && !user.getId().equals(obj.get().getCliente().getId())){
			throw new AuthorizationException("Acesso negado");
		}

		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}

	public Cidade findCidade(UUID id)
	{

		var obj = cidadeRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}

	public Endereco handle(CriarEnderecoCommand cmd)
	{
		var endereco = Endereco.from(cmd);
		return repository.save(endereco);
	}

	public Endereco handle(AlterarEnderecoCommand cmd)
	{
		var endereco = this.findEndereco(cmd.getId());
		endereco.alterar(cmd);
		return repository.save(endereco);
	}

	public Cidade hande(CriarCidadeCommand cmd){
		var cidade = Cidade.from(cmd);
		return cidadeRepository.save(cidade);
	}

	public Cidade handle(AlterarCidadeCommand cmd)
	{
		var cidade = this.findCidade(cmd.getId());
		cidade.alterar(cmd);
		return cidadeRepository.save(cidade);
	}

}
