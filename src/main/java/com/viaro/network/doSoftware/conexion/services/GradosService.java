package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.Grado;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import com.viaro.network.doSoftware.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;

@Service
public class GradosService {
    @Autowired
    private BaseDatos db;

    public GradosService(){}

    public LinkedList<Grado> getGrados(){
        LinkedList<Grado> resultado = null;
        try{
            db.conectarDB();
            String sql = "SELECT GR_ID, GR_NOMBRE, CONCAT(PR_NOMBRE,' ',PR_APELLIDOS)" +
                    " FROM Grado " +
                    " INNER JOIN PROFESOR ON PR_ID = GR_PROFESOR \n" +
                    "ORDER BY GR_ID ASC";
            ResultSet rs = db.consulta(sql);
            resultado = new LinkedList<>();
            while(rs.next()){
                resultado.add(new Grado(rs.getInt(1),rs.getString(2),null,
                        rs.getString(3)));
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
            String dml = "{call P_DELETE_Grado(?) }";
            db.dml(dml,id);
            db.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }

    }

    public boolean create(Grado grado) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_Grado(null,?,?) }";
            db.dml(dml,grado.getNombre(),grado.getProfesor());
            db.commit();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return flag;
    }

    public Grado getGrado(Long id) {
        Grado resultado = null;
        try {
            db.conectarDB();
            String sql = "SELECT GR_ID, GR_NOMBRE, GR_PROFESOR FROM Grado " +
                    " WHERE GR_ID = ?";
            ResultSet rs = db.consulta(sql,id);
            if(rs.next()){
                resultado = new Grado(rs.getInt(1),rs.getString(2),rs.getInt(3),null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return resultado;
    }

    public boolean editar(Grado grado) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_Grado(?,?,?) }";
            db.dml(dml,grado.getId(),grado.getNombre(),grado.getProfesor());
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
