package com.jordanpena.api.excecao;


@SuppressWarnings("serial")
public class ExcecaoArmazenamento extends RuntimeException {
    public ExcecaoArmazenamento(String msg) {
        super(msg);
    }

    public ExcecaoArmazenamento(String msg, Throwable causa) {
        super(msg, causa);
    }
}