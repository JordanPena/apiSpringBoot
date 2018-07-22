package com.jordanpena.api.controle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jordanpena.api.modelo.Matricula;
import com.jordanpena.api.repositorio.RepositorioMatricula;

public class ControleMatricula {

	private Matricula matricula;
	private RepositorioMatricula repo;

	public ControleMatricula(String numeroMatricula, String digitoVerificador) {
		this.matricula = new Matricula(numeroMatricula);
		if (digitoVerificador == null) {
			this.gerarDigitoVerificador(numeroMatricula);
		}else {
			this.matricula.setDigitoVerificador(digitoVerificador);
			this.gerarIndicador();
		}
		// this.repo.save(this.matricula);
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public void gerarDigitoVerificador(String numeroMatricula) {
		String dv = this.calculaDigitoVerificador(numeroMatricula);
		this.matricula.setDigitoVerificador(dv);
	}

	public void gerarIndicador() {
		
		String dv = this.matricula.getDigitoVerificador();
		
		String digitoOK = this.calculaDigitoVerificador(this.matricula.getNumero());
		
		if(dv.equalsIgnoreCase(digitoOK)) {
			this.matricula.setIdentificador(true);
		}else {
			this.matricula.setIdentificador(false);
		}
	}

	private String calculaDigitoVerificador(String numero) {

		int total = 0;
		int somaParcial = 0;
		int aux = 5;

		char[] num = numero.toCharArray();

		for (int i = 0; i < num.length; i++) {

			int atual = Integer.parseInt(String.valueOf(num[i]));

			int parcial = atual * aux;

			somaParcial = +parcial;

			total += somaParcial;

			aux--;
		}

		int resto = total % 16;

		return Integer.toString(resto, 16).toUpperCase();
	}
	
	public String recuperarMatriculaComDV() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.matricula.getNumero());
		sb.append("-");
		sb.append(this.matricula.getDigitoVerificador());
		sb.append("\r");
		
		return sb.toString();
	}
	
	public String recuperarMatriculaVerificada() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.matricula.getNumero());
		sb.append("-");
		sb.append(this.matricula.getDigitoVerificador());		
		String status = this.matricula.getIdentificador() ? "verdadeiro" : "falso";
		sb.append(" " + status);
		sb.append("\r");
		
		return sb.toString();
	}

}