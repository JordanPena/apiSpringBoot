package com.jordanpena.api.modelo;

public class Resposta {
    private String nomeArquivo;
    private String tipoArquivo;
    private long tamanho;
    private String uriDownload;

    public Resposta(String nomeArquivo, String uriDownload, long tamanho, String tipoArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.tipoArquivo = tipoArquivo;
        this.tamanho = tamanho;
        this.uriDownload = uriDownload;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getUriDownload() {
        return uriDownload;
    }

    public void setUriDownload(String uriDownload) {
        this.uriDownload = uriDownload;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }
}