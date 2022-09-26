package com.viaro.network.doSoftware.controller;

import com.viaro.network.doSoftware.bean.Alumno;
import com.viaro.network.doSoftware.bean.Profesor;
import com.viaro.network.doSoftware.conexion.services.AlumnosService;
import com.viaro.network.doSoftware.conexion.services.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController()
@RequestMapping(value = "alumnos")
public class AlumnosController {
    @Autowired
    private AlumnosService service;

    @GetMapping(value = "/list")
    public List<Alumno> getList(){
        return service.getAlumnos();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping(value = "/create")
    public boolean guardar(@RequestBody Alumno Alumno){
        System.out.println(Alumno);
        if(Alumno.getNombres().isEmpty() || Alumno.getApellidos().isEmpty() || Alumno.getGenero().isEmpty()){
            return false;
        }else {
            return service.create(Alumno);
        }
    }

    @GetMapping(value = "/editar/{id}")
    public Alumno editar(@PathVariable Long id){
        return service.getAlumno(id);
    }

    @PostMapping(value = "/editaralumno")
    public boolean editar(@RequestBody Alumno alumno){
        System.out.println(alumno);
        if(alumno.getId() == null || alumno.getNombres().isEmpty() || alumno.getApellidos().isEmpty() || alumno.getGenero().isEmpty()){
            return false;
        }else {
            return service.editar(alumno);
        }
    }
}
