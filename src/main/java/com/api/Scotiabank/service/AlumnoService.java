package com.api.Scotiabank.service;

import com.api.Scotiabank.dto.AlumnoRequestDTO;
import com.api.Scotiabank.dto.AlumnoResponseDTO;
import com.api.Scotiabank.entity.Alumno;
import com.api.Scotiabank.entity.Estado;
import com.api.Scotiabank.exceptionHandler.ErrorCodedException;
import com.api.Scotiabank.exceptionHandler.ErrorsHandle;
import com.api.Scotiabank.repository.AlumnoRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AlumnoService implements IAlumnoService {

    @Autowired private AlumnoRepository alumnoRepository;

    @Override
    public Mono<Void> saveAlumno(AlumnoRequestDTO alumno) throws Exception {

        return alumnoRepository
                .existsByAlumnoId(alumno.getId())
                .flatMap(
                        exists -> {
                            if (exists) {
                                return Mono.error(new ErrorCodedException(ErrorsHandle.E_ALUMNO_ID_DUPLICATED));
                            } else {
                                return alumnoRepository
                                        .save(this.mapearDTOaEntidadAlumno(alumno))
                                        .then(Mono.empty());
                            }
                        });
    }

    public Alumno mapearDTOaEntidadAlumno(AlumnoRequestDTO alumno) {
        return Alumno.builder()
                .id(alumno.getId())
                .nombre(alumno.getNombre())
                .apellido(alumno.getApellido())
                .estado(alumno.getEstado())
                .edad(alumno.getEdad())
                .build();
    }

    @Override
    public Flux<AlumnoResponseDTO> getActiveAlumnos() {
        return alumnoRepository
                .findByEstado(Estado.ACTIVO)
                .flatMap(
                        alumno -> {
                            return Optional.ofNullable(alumno)
                                    .map(a -> Flux.just(AlumnoResponseDTO.from(a)))
                                    .orElseGet(Flux::empty);
                        });
    }
}
