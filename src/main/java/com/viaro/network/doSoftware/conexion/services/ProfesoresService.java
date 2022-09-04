package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.Profesor;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;

@Service
public class ProfesoresService {
    @Autowired
    private BaseDatos db;

    public ProfesoresService(){}

    public LinkedList<Profesor> getProfesores(){
        LinkedList<Profesor> resultado = null;
        try{
            db.conectarDB();
            String sql = "SELECT PR_ID, PR_NOMBRE, PR_APELLIDOS, IF(PR_GENERO='M','Masculino','Femenino') FROM PROFESOR ORDER BY PR_ID ASC";
            ResultSet rs = db.consulta(sql);
            resultado = new LinkedList<>();
            while(rs.next()){
                resultado.add(new Profesor(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        System.out.println(resultado);
        return resultado;
    }

    public void delete(Long id) {
        try{
            db.conectarDB();
            String dml = "{call P_DELETE_PROFESOR(?) }";
            db.dml(dml,id);
            db.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }

    }

    public boolean create(Profesor profesor) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_PROFESOR(null,?,?,?) }";
            db.dml(dml,profesor.getNombres(),profesor.getApellidos(),profesor.getGenero());
            db.commit();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return flag;
    }

    public Profesor getProfesor(Long id) {
        Profesor resultado = null;
        try {
            db.conectarDB();
            String sql = "SELECT PR_ID, PR_NOMBRE, PR_APELLIDOS, PR_GENERO FROM PROFESOR " +
                    " WHERE PR_ID = ?";
            ResultSet rs = db.consulta(sql,id);
            if(rs.next()){
                resultado = new Profesor(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return resultado;
    }

    public boolean editar(Profesor profesor) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_PROFESOR(?,?,?,?) }";
            db.dml(dml,profesor.getId(),profesor.getNombres(),profesor.getApellidos(),profesor.getGenero());
            db.commit();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        System.out.println(flag);
        return flag;
    }
}
