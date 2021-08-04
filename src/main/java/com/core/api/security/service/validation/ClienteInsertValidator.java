package com.core.api.security.service.validation;

import com.core.api.cliente.domain.ClienteRepository;
import com.core.api.security.service.validation.utils.BR;
import com.core.api.security.userss.controller.exception.FieldErrorMessage;
import com.core.api.cliente.api.dto.CriarClienteDTO;
import com.core.api.cliente.domain.Cliente;
import com.core.api.cliente.domain.enums.TipoCliente;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, CriarClienteDTO>
{


	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann)
	{
	}

	@Override
	public boolean isValid(CriarClienteDTO objDto, ConstraintValidatorContext context)
	{
		List<FieldErrorMessage> list = new ArrayList<>();

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCode()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldErrorMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCode()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldErrorMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldErrorMessage("email", "Email já existente"));
		}

		for (FieldErrorMessage e : list)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}


