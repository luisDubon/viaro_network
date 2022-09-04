package com.viaro.network.doSoftware.controller;

import com.viaro.network.doSoftware.bean.Estadistica;
import com.viaro.network.doSoftware.conexion.services.EstadisticaService;
import com.viaro.network.doSoftware.conexion.services.PruebaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private PruebaServices pruebaServices;
    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping("/connection")
    public void getConnection(){
       // pruebaServices.getPruebaConexion();
    }

    @GetMapping("/estadistica")
    public Estadistica getEstadistica(){
        return estadisticaService.getEstadistica();
    }
}
