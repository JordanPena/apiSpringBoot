package com.jordanpena.api.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExcecaoArquivoNaoEncontrado extends RuntimeException {
    public ExcecaoArquivoNaoEncontrado(String message) {
        super(message);
    }

    public ExcecaoArquivoNaoEncontrado(String message, Throwable cause) {
        super(message, cause);
    }
}