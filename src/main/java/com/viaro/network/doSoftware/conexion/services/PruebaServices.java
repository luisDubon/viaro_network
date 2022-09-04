package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

@Service
public class PruebaServices {
    @Autowired
    private BaseDatos db;

    public PruebaServices(){}

    public void getPruebaConexion(){
        try{
            db.conectarDB();
            String sql = "SELECT 'SUCCESFULL CONECTION' FROM DUAL";
            ResultSet rs = db.consulta(sql);
            if(rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
    }
}
