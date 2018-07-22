package com.jordanpena.api.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Matricula {

	@Id
    @GeneratedValue
    private Long id;
    private String numero;
    private String digitoVerificador;
    private boolean identificador;
    
	public Matricula(String numero) {
    	this.numero = numero;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getDigitoVerificador() {
		return digitoVerificador;
	}

	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}

	public boolean getIdentificador() {
		return identificador;
	}

	public void setIdentificador(boolean identificador) {
		this.identificador = identificador;
	}

    @Override
    public String toString() {
        return "" + numero+   
               "-" + digitoVerificador+
               " " + identificador+"\r";
    }
    
}
