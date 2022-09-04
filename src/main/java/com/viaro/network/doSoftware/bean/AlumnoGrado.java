package com.viaro.network.doSoftware.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoGrado {
    private Integer id;
    private Integer alumno;
    private Integer grado;
    private String seccion;
    private String alumnoVr;
    private String gradoVr;
}
