package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.Alumno;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import com.viaro.network.doSoftware.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlumnosService {
    @Autowired
    private BaseDatos db;

    public AlumnosService(){}

    public List<Alumno> getAlumnos(){
        try{
            String sql = "SELECT AL_ID, AL_NOMBRE, AL_APELLIDO, IF(AL_GENERO='M','Masculino','Femenino'), AL_FECHA_NACIMIENTO, DATE_FORMAT(AL_FECHA_NACIMIENTO, '%d/%m/%Y')," +
                    " TIMESTAMPDIFF(YEAR,AL_FECHA_NACIMIENTO,CURDATE())" +
                    " FROM ALUMNO ORDER BY AL_ID ASC";

            return  db.getList(sql, (rs, i)->{
                Alumno alumno = new Alumno(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4), DateUtils.convertToLocalDate(rs.getDate(5)),rs.getString(6),rs.getString(7));
                return alumno;
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Long id) {
        try{
            String dml = "{call P_DELETE_ALUMNO(?) }";
            db.call(dml,id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean create(Alumno alumno) {
        boolean flag = false;
        try{
            String dml = "{call P_ADD_ALUMNO(null,?,?,?,?) }";
            db.call(dml,alumno.getNombres(),alumno.getApellidos(),alumno.getGenero(), alumno.getFechaNacimiento());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public Alumno getAlumno(Long id) {
        try {
            String sql = "SELECT AL_ID, AL_NOMBRE, AL_APELLIDO, AL_GENERO, AL_FECHA_NACIMIENTO, AL_FECHA_NACIMIENTO, DATE_FORMAT(AL_FECHA_NACIMIENTO, '%d/%m/%Y')," +
                    " TIMESTAMPDIFF(YEAR,AL_FECHA_NACIMIENTO,CURDATE()) FROM ALUMNO " +
                    " WHERE AL_ID = ?";

            return db.getItem(sql,(rs,i)->{
                Alumno alumno = new Alumno(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4), DateUtils.convertToLocalDate(rs.getDate(5)),rs.getString(6),rs.getString(7));
                return alumno;
            },id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean editar(Alumno alumno) {
        boolean flag = false;
        try{
            String dml = "{call P_ADD_ALUMNO(?,?,?,?,?) }";
            db.call(dml,alumno.getId(),alumno.getNombres(),alumno.getApellidos(),alumno.getGenero(),alumno.getFechaNacimiento());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }
}
