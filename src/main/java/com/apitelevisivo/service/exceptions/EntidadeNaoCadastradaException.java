package com.apitelevisivo.service.exceptions;

public class EntidadeNaoCadastradaException extends NegocioException {

    private static final long serialVersionUID = 3938661863233960823L;

    public EntidadeNaoCadastradaException(String message) {
        super(message);
    }
}