package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.Alumno;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import com.viaro.network.doSoftware.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;

@Service
public class AlumnosService {
    @Autowired
    private BaseDatos db;

    public AlumnosService(){}

    public LinkedList<Alumno> getAlumnos(){
        LinkedList<Alumno> resultado = null;
        try{
            db.conectarDB();
            String sql = "SELECT AL_ID, AL_NOMBRE, AL_APELLIDO, IF(AL_GENERO='M','Masculino','Femenino'), AL_FECHA_NACIMIENTO, DATE_FORMAT(AL_FECHA_NACIMIENTO, '%d/%m/%Y')," +
                    " TIMESTAMPDIFF(YEAR,AL_FECHA_NACIMIENTO,CURDATE())" +
                    " FROM ALUMNO ORDER BY AL_ID ASC";
            ResultSet rs = db.consulta(sql);
            resultado = new LinkedList<>();
            while(rs.next()){
                resultado.add(new Alumno(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4), DateUtils.convertToLocalDate(rs.getDate(5)),rs.getString(6),rs.getString(7)));
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
            String dml = "{call P_DELETE_ALUMNO(?) }";
            db.dml(dml,id);
            db.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }

    }

    public boolean create(Alumno alumno) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_ALUMNO(null,?,?,?,?) }";
            db.dml(dml,alumno.getNombres(),alumno.getApellidos(),alumno.getGenero(), alumno.getFechaNacimiento());
            db.commit();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return flag;
    }

    public Alumno getAlumno(Long id) {
        Alumno resultado = null;
        try {
            db.conectarDB();
            String sql = "SELECT AL_ID, AL_NOMBRE, AL_APELLIDO, AL_GENERO, AL_FECHA_NACIMIENTO, AL_FECHA_NACIMIENTO, DATE_FORMAT(AL_FECHA_NACIMIENTO, '%d/%m/%Y')," +
                    " TIMESTAMPDIFF(YEAR,AL_FECHA_NACIMIENTO,CURDATE()) FROM ALUMNO " +
                    " WHERE AL_ID = ?";
            ResultSet rs = db.consulta(sql,id);
            if(rs.next()){
                resultado = new Alumno(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4), DateUtils.convertToLocalDate(rs.getDate(5)),rs.getString(6),rs.getString(7));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return resultado;
    }

    public boolean editar(Alumno alumno) {
        boolean flag = false;
        try{
            db.conectarDB();
            String dml = "{call P_ADD_ALUMNO(?,?,?,?,?) }";
            db.dml(dml,alumno.getId(),alumno.getNombres(),alumno.getApellidos(),alumno.getGenero(),alumno.getFechaNacimiento());
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
