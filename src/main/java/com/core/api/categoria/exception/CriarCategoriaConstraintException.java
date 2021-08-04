package com.core.api.categoria.exception;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Getter
public class CriarCategoriaConstraintException extends ConstraintViolationException {


    private static final long serialVersionUID = 7927953991751530655L;

    public CriarCategoriaConstraintException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
