package com.jordanpena.api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jordanpena.api.modelo.Matricula;

@RepositoryRestResource
public interface RepositorioMatricula extends JpaRepository<Matricula, Long> {

}
