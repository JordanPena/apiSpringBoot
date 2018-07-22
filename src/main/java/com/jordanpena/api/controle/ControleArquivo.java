package com.jordanpena.api.controle;

import org.apache.tomcat.util.descriptor.web.MultipartDef;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jordanpena.api.modelo.Matricula;
import com.jordanpena.api.modelo.Resposta;
import com.jordanpena.api.servico.GuardarArquivo;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ControleArquivo {

	private static final Logger logger = LoggerFactory.getLogger(ControleArquivo.class);

	@Autowired
	private GuardarArquivo guardarArquivo;

	private InputStream inputStream;
	private BufferedReader bufferedReader;
	private ControleMatricula ctlMatricula;

	@PostMapping("/upload")
	public Resposta upload(@RequestParam("file") MultipartFile file) throws IOException {

		if (!file.isEmpty()) {

			String nomeArquivo = guardarArquivo.salvar(file);
			String nomeArquivoNovo = "";
			File arquivoNovo = null;

			try {
				inputStream = file.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				String linha;
				if ("matriculasSemDV.txt".equalsIgnoreCase(nomeArquivo)) {

					Pattern patternSemDV = Pattern.compile("(^\\d{4}$)");

					arquivoNovo = new File("matriculasComDV.txt");
					FileWriter fw = new FileWriter(arquivoNovo.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);

					while ((linha = bufferedReader.readLine()) != null) {

						Matcher match = patternSemDV.matcher(linha);
						if (match.matches()) {

							ctlMatricula = new ControleMatricula(match.group(0), null);

						} else {
							// TODO disparar exception ou salvar lista erros
						}

						bw.write(ctlMatricula.recuperarMatriculaComDV());
					}
					bw.close();					
					nomeArquivoNovo = guardarArquivo.salvarFile(arquivoNovo);
					
				} else if ("matriculasParaVerificar.txt".equalsIgnoreCase(nomeArquivo)) {

					Pattern patternComDV = Pattern.compile("(^\\d{4}-[0-9A-F]$)");

					arquivoNovo = new File("matriculasVerificadas.txt");
					FileWriter fw = new FileWriter(arquivoNovo.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);

					while ((linha = bufferedReader.readLine()) != null) {

						Matcher match = patternComDV.matcher(linha);
						if (match.matches()) {

							String numero = match.group(0).split("-")[0];
							String digitoVerificador = match.group(0).split("-")[1];

							ctlMatricula = new ControleMatricula(numero, digitoVerificador);

							System.out.println(ctlMatricula.recuperarMatriculaVerificada());

						} else {
							// TODO disparar exception ou salvar lista erros
						}

						bw.write(ctlMatricula.getMatricula().toString());
						
					}
					bw.close();					
					nomeArquivoNovo = guardarArquivo.salvarFile(arquivoNovo);
					
				}

			} catch (Exception e) {

			}
			System.out.println(nomeArquivoNovo);
			String uriDownload = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(nomeArquivoNovo).toUriString();

			return new Resposta(nomeArquivoNovo, uriDownload, file.getSize(), file.getContentType());
		}
		return null;
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = guardarArquivo.lerArquivo(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
