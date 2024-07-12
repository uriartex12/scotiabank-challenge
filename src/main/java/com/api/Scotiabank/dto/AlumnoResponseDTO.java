package com.api.Scotiabank.dto;

import com.api.Scotiabank.entity.Alumno;
import com.api.Scotiabank.entity.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlumnoResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;

    public static AlumnoResponseDTO from(Alumno alumno) {
        AlumnoResponseDTO dto =
                AlumnoResponseDTO.builder()
                        .id(alumno.getId())
                        .nombre(alumno.getNombre())
                        .apellido(alumno.getApellido())
                        .estado(alumno.getEstado())
                        .edad(alumno.getEdad())
                        .build();
        return dto;
    }
}
