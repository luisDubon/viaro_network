package com.viaro.network.doSoftware.controller;
import com.viaro.network.doSoftware.bean.Grado;
import com.viaro.network.doSoftware.conexion.services.GradosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController()
@RequestMapping(value = "grados")
public class GradoController {
    @Autowired
    private GradosService service;

    @GetMapping(value = "/list")
    public List<Grado> getList(){
        return service.getGrados();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping(value = "/create")
    public boolean guardar(@RequestBody Grado grado){
        System.out.println(grado);
        if(grado.getNombre().isEmpty() || grado.getProfesor()  == null ){
            return false;
        }else {
            return service.create(grado);
        }
    }

    @GetMapping(value = "/editar/{id}")
    public Grado editar(@PathVariable Long id){
        return service.getGrado(id);
    }

    @PostMapping(value = "/editargrado")
    public boolean editar(@RequestBody Grado grado){
        System.out.println(grado);
        if(grado.getId() == null || grado.getProfesor() ==null){
            return false;
        }else {
            return service.editar(grado);
        }
    }
}
