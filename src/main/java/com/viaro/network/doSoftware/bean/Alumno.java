package com.viaro.network.doSoftware.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String genero;
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate fechaNacimiento;
    private String fechaNacVar;
    private String edad;
}
