package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.Mapping.EstadisticaRowMapper;
import com.viaro.network.doSoftware.bean.Estadistica;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

@Service
public class EstadisticaService {
    @Autowired
    private JdbcTemplate db;

    private EstadisticaService(){

    }

    public Estadistica getEstadistica(){
        Estadistica resultado = null;
        try{
            String sql = "SELECT T.ALUMG ag, F.ALIM am, P.PRF pf, D.GRD gd FROM (\n" +
                    "(SELECT COUNT(*) ALUMG FROM ALUMNOGRADO) T,\n" +
                    "(SELECT COUNT(*) ALIM FROM ALUMNO) F,\n" +
                    "(SELECT COUNT(*) PRF FROM PROFESOR) P,\n" +
                    "(SELECT COUNT(*) GRD FROM GRADO) D\n" +
                    ")";
            resultado = db.queryForObject(sql, new EstadisticaRowMapper());
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
}
