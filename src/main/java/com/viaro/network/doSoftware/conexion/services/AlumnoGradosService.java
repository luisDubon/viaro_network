package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.AlumnoGrado;
import com.viaro.network.doSoftware.bean.Grado;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlumnoGradosService {
    @Autowired
    private BaseDatos db;

    public AlumnoGradosService(){}

    public List<AlumnoGrado> getGrados(){
        try{
            String sql = "SELECT AG_ID, CONCAT(AL_NOMBRE,' ',AL_APELLIDO), CONCAT(GR_NOMBRE,' (',PR_NOMBRE,' ',PR_APELLIDOS,')'), " +
                    " AG_SECCION " +
                    " FROM ALUMNOGRADO" +
                    "  INNER JOIN ALUMNO ON AL_ID = AG_ALUMNO_ID " +
                    "  INNER JOIN GRADO ON GR_ID = AG_GRADO_ID " +
                    " INNER JOIN PROFESOR ON PR_ID = GR_PROFESOR \n" +
                    "ORDER BY GR_ID ASC";

            return db.getList(sql,(rs, i)->{
                AlumnoGrado alumnoGrado = new AlumnoGrado(rs.getInt(1),null,null,rs.getString(4),rs.getString(2),
                        rs.getString(3));
                return alumnoGrado;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Long id) {
        try{
            String dml = "{call P_DELETE_ALUMNO_GRADO(?) }";
            db.call(dml,id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean create(AlumnoGrado alumnoGrado) {
        boolean flag = false;
        try{
            String dml = "{call P_ADD_ALUMNO_GRADO(null,?,?,?) }";
            db.call(dml,alumnoGrado.getAlumno(),alumnoGrado.getGrado(),alumnoGrado.getSeccion());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }

    public AlumnoGrado getAlumnoGrado(Long id) {
        try {
            String sql = "SELECT AG_ID, AG_ALUMNO_ID, AG_GRADO_ID, " +
            " AG_SECCION " +
                    " FROM ALUMNOGRADO"+
                    " WHERE AG_ID = ?";
            return db.getItem(sql,(rs,i)->{
                AlumnoGrado alumnoGrado = new AlumnoGrado(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),null,null);
                return alumnoGrado;
            },id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean editar(AlumnoGrado alumnoGrado) {
        boolean flag = false;
        try{
            String dml = "{call P_ADD_ALUMNO_GRADO(?,?,?,?) }";
            db.call(dml,alumnoGrado.getId(),alumnoGrado.getAlumno(),alumnoGrado.getGrado(),alumnoGrado.getSeccion());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
