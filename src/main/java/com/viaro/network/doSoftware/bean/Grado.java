package com.viaro.network.doSoftware.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grado {
    private Integer id;
    private String nombre;
    private Integer profesor;
    private String profeName;
}
