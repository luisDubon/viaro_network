package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.Grado;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import com.viaro.network.doSoftware.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class GradosService {
    @Autowired
    private BaseDatos db;

    public GradosService(){}

    public List<Grado> getGrados(){
        try{
            String sql = "SELECT GR_ID, GR_NOMBRE, CONCAT(PR_NOMBRE,' ',PR_APELLIDOS)" +
                    " FROM Grado " +
                    " INNER JOIN PROFESOR ON PR_ID = GR_PROFESOR \n" +
                    "ORDER BY GR_ID ASC";

            return  db.getList(sql,(rs, i)->{
                Grado grado = new Grado(rs.getInt(1),rs.getString(2),null,
                        rs.getString(3));
                return grado;
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return null;
    }

    public void delete(Long id) {
        try{
            String dml = "{call P_DELETE_Grado(?) }";
            db.call(dml,id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean create(Grado grado) {
        boolean flag = false;
        try{
            String dml = "{call P_ADD_Grado(null,?,?) }";
            db.call(dml,grado.getNombre(),grado.getProfesor());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public Grado getGrado(Long id) {
        try {
            String sql = "SELECT GR_ID, GR_NOMBRE, GR_PROFESOR FROM Grado " +
                    " WHERE GR_ID = ?";
            return db.getItem(sql,(rs, i)->{
                Grado grado = new Grado(rs.getInt(1),rs.getString(2),rs.getInt(3),null);
                return grado;
            },id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean editar(Grado grado) {
        boolean flag = false;
        try{
            String dml = "{call P_ADD_Grado(?,?,?) }";
            db.call(dml,grado.getId(),grado.getNombre(),grado.getProfesor());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
