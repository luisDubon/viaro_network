package com.viaro.network.doSoftware.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profesor {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String genero;
}
