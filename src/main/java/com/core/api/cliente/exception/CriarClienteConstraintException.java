package com.core.api.cliente.exception;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Getter
public class CriarClienteConstraintException extends ConstraintViolationException {


    private static final long serialVersionUID = 6751884628167754859L;

    public CriarClienteConstraintException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
