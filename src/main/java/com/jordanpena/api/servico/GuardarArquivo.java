package com.jordanpena.api.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jordanpena.api.config.ConfigStorage;
import com.jordanpena.api.excecao.ExcecaoArmazenamento;
import com.jordanpena.api.excecao.ExcecaoArquivoNaoEncontrado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class GuardarArquivo {

	private final Path localArmazenamento;

	@Autowired
	public GuardarArquivo(ConfigStorage configStorage) {

		this.localArmazenamento = Paths.get(configStorage.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.localArmazenamento);
		} catch (Exception ex) {
			throw new ExcecaoArmazenamento("Não foi possível armazenar o arquivo anexado.", ex);
		}
	}

	public String salvar(MultipartFile file) {

		String nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// verifica se o nome do arquivo e valido
			if (nomeArquivo.contains("..")) {
				throw new ExcecaoArmazenamento("Desculpe, o nome do aquivo é inválido.");
			}

			// copia arquivo para o detino
			Path detino = this.localArmazenamento.resolve(nomeArquivo);
			Files.copy(file.getInputStream(), detino, StandardCopyOption.REPLACE_EXISTING);

			return nomeArquivo;

		} catch (IOException ex) {
			throw new ExcecaoArmazenamento(
					"Não foi possível ler o arquivo " + nomeArquivo + ". Por favor, tente novamente!", ex);
		}
	}
	
	public String salvarFile(File file) throws FileNotFoundException {

		String nomeArquivo = StringUtils.cleanPath(file.getName());
		InputStream is = new FileInputStream(file);
		

		try {
			
			if (nomeArquivo.contains("..")) {
				throw new ExcecaoArmazenamento("Desculpe, o nome do aquivo é inválido.");
			}

			// copia arquivo para o detino
			Path detino = this.localArmazenamento.resolve(nomeArquivo);
			Files.copy(is, detino, StandardCopyOption.REPLACE_EXISTING);

			return nomeArquivo;

		} catch (IOException ex) {
			throw new ExcecaoArmazenamento(
					"Não foi possível ler o arquivo " + nomeArquivo + ". Por favor, tente novamente!", ex);
		}
	}

	public Resource lerArquivo(String nome) {
		try {
			Path caminho = this.localArmazenamento.resolve(nome).normalize();
			Resource resource = new UrlResource(caminho.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ExcecaoArquivoNaoEncontrado("Arquivo não encontrado " + nome);
			}
		} catch (MalformedURLException ex) {
			throw new ExcecaoArquivoNaoEncontrado("Arquivo não encontrado " + nome, ex);
		}
	}
}