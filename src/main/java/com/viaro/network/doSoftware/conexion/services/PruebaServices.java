package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

@Service
public class PruebaServices {
    @Autowired
    private JdbcTemplate db;

    public PruebaServices(){}

    public void getPruebaConexion(){
        try{
            String sql = "SELECT 'SUCCESFULL CONECTION' FROM DUAL";

                System.out.println(db.queryForObject(sql,String.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
