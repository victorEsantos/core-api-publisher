package com.core.api.pagamento.exception;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Getter
public class CriarCidadeConstraintException extends ConstraintViolationException {


    private static final long serialVersionUID = 6751884628167754859L;

    public CriarCidadeConstraintException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
