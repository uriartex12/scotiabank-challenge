package com.api.Scotiabank.controller;

import com.api.Scotiabank.dto.AlumnoRequestDTO;
import com.api.Scotiabank.dto.AlumnoResponseDTO;
import com.api.Scotiabank.service.IAlumnoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/alumno")
@Slf4j
public class AlumnoController {

    @Autowired private IAlumnoService alumnoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createAlumno(@Validated @RequestBody AlumnoRequestDTO alumno) throws Exception {
        return alumnoService.saveAlumno(alumno);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<AlumnoResponseDTO> getActiveAlumnos() throws Exception {
        return alumnoService.getActiveAlumnos();
    }
}
