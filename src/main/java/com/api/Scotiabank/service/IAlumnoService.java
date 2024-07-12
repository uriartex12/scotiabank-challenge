package com.api.Scotiabank.service;

import com.api.Scotiabank.dto.AlumnoRequestDTO;
import com.api.Scotiabank.dto.AlumnoResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlumnoService {

    public Mono<Void> saveAlumno(AlumnoRequestDTO alumno) throws Exception;

    public Flux<AlumnoResponseDTO> getActiveAlumnos() throws Exception;
}
