package com.api.Scotiabank;

import com.api.Scotiabank.controller.AlumnoController;
import com.api.Scotiabank.dto.AlumnoRequestDTO;
import com.api.Scotiabank.dto.AlumnoResponseDTO;
import com.api.Scotiabank.entity.Alumno;
import com.api.Scotiabank.entity.Estado;
import com.api.Scotiabank.repository.AlumnoRepository;
import com.api.Scotiabank.service.AlumnoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = AlumnoController.class)
@Import({AlumnoService.class, TestDatabaseConfiguration.class})
class AlumnoControllerIntegrationTest {

    @Autowired private WebTestClient webTestClient;

    @MockBean private AlumnoRepository alumnoRepository;

    @Test
    @DisplayName("GIVEN data correct WHEN REGISTER STUDENT method THEN OK.OO")
    void testRegisterStudentOk() throws Exception {
        // Given
        AlumnoRequestDTO alumnodto =
                AlumnoRequestDTO.builder()
                        .id(1)
                        .nombre("Jhon")
                        .apellido("Uriarte")
                        .estado(Estado.ACTIVO)
                        .edad(25)
                        .build();

        Mockito.when(alumnoRepository.existsByAlumnoId(alumnodto.getId())).thenReturn(Mono.just(false));

        Mockito.when(alumnoRepository.save(Mockito.any(Alumno.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // When
        webTestClient
                .post()
                .uri("/api/v2/alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(alumnodto), AlumnoRequestDTO.class)
                .exchange()
                // Then
                .expectStatus()
                .isCreated()
                .expectBody()
                .isEmpty();

        Mockito.verify(alumnoRepository, Mockito.times(1)).save(Mockito.any(Alumno.class));
    }

    @Test
    @DisplayName("GIVEN alumnoId duplicated WHEN REGISTER STUDENT method THEN ERROR")
    void testRegisterStudentExists() throws Exception {
        // Given
        Alumno alumno =
                Alumno.builder()
                        .id(1)
                        .nombre("Jhon")
                        .apellido("Uriarte")
                        .estado(Estado.ACTIVO)
                        .edad(25)
                        .build();

        Mockito.when(alumnoRepository.existsByAlumnoId(alumno.getId())).thenReturn(Mono.just(true));

        // When
        webTestClient
                .post()
                .uri("/api/v2/alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(alumno), Alumno.class)
                .exchange()

                // Then
                .expectStatus()
                .is5xxServerError()
                .expectBody()
                .jsonPath("$.status")
                .isEqualTo(500)
                .jsonPath("$.code")
                .isEqualTo("E_STUDENT_ID_DUPLICATED")
                .jsonPath("$.message")
                .isEqualTo("The student ID is duplicated.");
    }

    @Test
    @DisplayName("GIVEN params correct WHEN FIND_ALL_STUDENT method THEN OK.OO")
    void testFindAllStudents() throws Exception {
        // Given
        Alumno alumno1 =
                Alumno.builder()
                        .id(1)
                        .nombre("Jhon")
                        .apellido("Uriarte")
                        .estado(Estado.ACTIVO)
                        .edad(25)
                        .build();

        Alumno alumno2 =
                Alumno.builder()
                        .id(2)
                        .nombre("Jane")
                        .apellido("Vargaz")
                        .estado(Estado.ACTIVO)
                        .edad(27)
                        .build();

        Mockito.when(alumnoRepository.findByEstado(Estado.ACTIVO))
                .thenReturn(Flux.just(alumno1, alumno2));

        webTestClient
                .get()
                .uri("/api/v2/alumno")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(AlumnoResponseDTO.class)
                .hasSize(2);
        
    }
}
