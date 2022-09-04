package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.AlumnoGrado;
import com.viaro.network.doSoftware.bean.Grado;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;

@Service
public class AlumnoGradosService {
    @Autowired
    private BaseDatos db;

    public AlumnoGradosService(){}

    public LinkedList<AlumnoGrado> getGrados(){
        LinkedList<AlumnoGrado> resultado = null;
        try{
            db.conectarDB();
            String sql = "SELECT AG_ID, CONCAT(AL_NOMBRE,' ',AL_APELLIDO), CONCAT(GR_NOMBRE,' (',PR_NOMBRE,' ',PR_APELLIDOS,')'), " +
                    " AG_SECCION " +
                    " FROM ALUMNOGRADO" +
                    "  INNER JOIN ALUMNO ON AL_ID = AG_ALUMNO_ID " +
                    "  INNER JOIN GRADO ON GR_ID = AG_GRADO_ID " +
                    " INNER JOIN PROFESOR ON PR_ID = GR_PROFESOR \n" +
                    "ORDER BY GR_ID ASC";
            ResultSet rs = db.consulta(sql);
            resultado = new LinkedList<>();
            while(rs.next()){
                resultado.add(new AlumnoGrado(rs.getInt(1),null,null,rs.getString(4),rs.getString(2),
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
            String dml = "{call P_DELETE_ALUMNO_GRADO(?) }";
            db.dml(dml,id);
            db.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }

    }

    public boolean create(AlumnoGrado alumnoGrado) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_ALUMNO_GRADO(null,?,?,?) }";
            db.dml(dml,alumnoGrado.getAlumno(),alumnoGrado.getGrado(),alumnoGrado.getSeccion());
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

    public AlumnoGrado getAlumnoGrado(Long id) {
        AlumnoGrado resultado = null;
        try {
            db.conectarDB();
            String sql = "SELECT AG_ID, AG_ALUMNO_ID, AG_GRADO_ID, " +
            " AG_SECCION " +
                    " FROM ALUMNOGRADO"+
                    " WHERE AG_ID = ?";
            ResultSet rs = db.consulta(sql,id);
            if(rs.next()){
                resultado = new AlumnoGrado(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),null,null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return resultado;
    }

    public boolean editar(AlumnoGrado alumnoGrado) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_ALUMNO_GRADO(?,?,?,?) }";
            db.dml(dml,alumnoGrado.getId(),alumnoGrado.getAlumno(),alumnoGrado.getGrado(),alumnoGrado.getSeccion());
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
