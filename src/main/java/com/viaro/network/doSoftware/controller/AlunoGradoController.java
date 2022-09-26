package com.viaro.network.doSoftware.controller;
import com.viaro.network.doSoftware.bean.AlumnoGrado;
import com.viaro.network.doSoftware.bean.Grado;
import com.viaro.network.doSoftware.conexion.services.AlumnoGradosService;
import com.viaro.network.doSoftware.conexion.services.GradosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController()
@RequestMapping(value = "alumnoGrados")
public class AlunoGradoController {
    @Autowired
    private AlumnoGradosService service;

    @GetMapping(value = "/list")
    public List<AlumnoGrado> getList(){
        return service.getGrados();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping(value = "/create")
    public boolean guardar(@RequestBody AlumnoGrado alumnoGrado){
        System.out.println(alumnoGrado);
        if(alumnoGrado.getAlumno() == null || alumnoGrado.getGrado()  == null || alumnoGrado.getSeccion().isEmpty()){
            return false;
        }else {
            return service.create(alumnoGrado);
        }
    }

    @GetMapping(value = "/editar/{id}")
    public AlumnoGrado editar(@PathVariable Long id){
        return service.getAlumnoGrado(id);
    }

    @PostMapping(value = "/editargrado")
    public boolean editar(@RequestBody AlumnoGrado alumnoGrado){
        System.out.println(alumnoGrado);
        if(alumnoGrado.getId() == null || alumnoGrado.getGrado() ==null || alumnoGrado.getSeccion().isEmpty()){
            return false;
        }else {
            return service.editar(alumnoGrado);
        }
    }
}
