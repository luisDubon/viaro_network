package com.viaro.network.doSoftware.controller;

import com.viaro.network.doSoftware.bean.Profesor;
import com.viaro.network.doSoftware.conexion.services.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController()
@RequestMapping(value = "profesores")
public class ProfesoresController {
    @Autowired
    private ProfesoresService service;

    @GetMapping(value = "/list")
    public LinkedList<Profesor> getList(){
        return service.getProfesores();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping(value = "/create")
    public boolean guardar(@RequestBody Profesor profesor){
        System.out.println(profesor);
        if(profesor.getNombres().isEmpty() || profesor.getApellidos().isEmpty() || profesor.getGenero().isEmpty()){
            return false;
        }else {
            return service.create(profesor);
        }
    }

    @GetMapping(value = "/editar/{id}")
    public Profesor editar(@PathVariable Long id){
        return service.getProfesor(id);
    }

    @PostMapping(value = "/editarprofesor")
    public boolean editar(@RequestBody Profesor profesor){
        System.out.println(profesor);
        if(profesor.getId() == null || profesor.getNombres().isEmpty() || profesor.getApellidos().isEmpty() || profesor.getGenero().isEmpty()){
            return false;
        }else {
            return service.editar(profesor);
        }
    }
}
