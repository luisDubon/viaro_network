package com.viaro.network.doSoftware.conexion.services;

import com.viaro.network.doSoftware.bean.Estadistica;
import com.viaro.network.doSoftware.conexion.db.BaseDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

@Service
public class EstadisticaService {
    @Autowired
    private BaseDatos db;

    private EstadisticaService(){

    }

    public Estadistica getEstadistica(){
        Estadistica resultado = null;
        try{
            db.conectarDB();
            String sql = "SELECT T.ALUMG ag, F.ALIM am, P.PRF pf, D.GRD gd FROM (\n" +
                    "(SELECT COUNT(*) ALUMG FROM ALUMNOGRADO) T,\n" +
                    "(SELECT COUNT(*) ALIM FROM ALUMNO) F,\n" +
                    "(SELECT COUNT(*) PRF FROM PROFESOR) P,\n" +
                    "(SELECT COUNT(*) GRD FROM GRADO) D\n" +
                    ")";
            ResultSet rs = db.consulta(sql);
            if(rs.next()){
                resultado = new Estadistica(rs.getString("ag"),rs.getString("am"),rs.getString("pf"),rs.getString("gd"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.desconectarBDD();
        }
        return resultado;
    }
}
