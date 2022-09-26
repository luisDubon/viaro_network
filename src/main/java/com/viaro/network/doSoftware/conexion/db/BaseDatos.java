package com.viaro.network.doSoftware.conexion.db;

import com.mysql.cj.MysqlType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class BaseDatos extends ConexionDB {
    public Connection conn = null;
    public PreparedStatement sentencia;
    public CallableStatement sentenciaFuncion;
    public ResultSet rs;
    @Value("${sql.url.connection}")
    private String cadenaConexion;
    @Value("${sql.usuario.test}")
    private String usr;
    @Value("${sql.password.test}")
    private String pass;
    @Value("${sql.class.name}")
    private String classNameDrive;

    public void conectarDB() throws Exception{
        Class.forName(classNameDrive);
        conn = DriverManager.getConnection(cadenaConexion,usr,pass);
        conn.setAutoCommit(false);
    }

    public void desconectarBDD(){
        try{
            if(rs != null){
                rs.close();
            }
        }catch (Exception e){}

        try{
            if(sentencia !=null){
                sentencia.close();
            }

            if(sentenciaFuncion != null){
                sentenciaFuncion.close();
            }
        }catch (Exception e){}

        try{
            if(conn != null && !conn.isClosed()){
                conn.rollback();
                conn.close();
            }
        }catch (Exception e){}
    }

    public ResultSet consulta(String sql) throws SQLException{
        sentencia = conn.prepareStatement(sql);
        rs = sentencia.executeQuery();
        return rs;
    }

    public ResultSet consulta(String sql, Object... parametros) throws SQLException{
        sentencia = conn.prepareStatement(sql);
        insertParametros(false,parametros);
        rs = sentencia.executeQuery();
        return rs;
    }

    public int dml(String sql, Object... parametros) throws Exception {
        sentencia = conn.prepareStatement(sql);
        insertParametros(false,parametros);
        return sentencia.executeUpdate();
    }

    public String llamarFuncion(String dml, Object... parametros) throws SQLException{
        String respuesta;
        sentenciaFuncion = conn.prepareCall(dml);
        sentenciaFuncion.registerOutParameter(1, MysqlType.VARCHAR);
        insertParametros(true,parametros);
        sentenciaFuncion.executeUpdate();
        respuesta = sentenciaFuncion.getString(1);

        return respuesta;
    }
    private void insertParametros(boolean isfuncion, Object... parametros) throws SQLException{
        int contador = isfuncion ? 1 : 0;
        for(Object parametro : parametros){
            if(conn.isClosed()){
                try {
                    conectarDB();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            contador++;
            if(parametro instanceof String){
                if(isfuncion){
                    sentenciaFuncion.setString(contador, (String) parametro);
                }else {
                    sentencia.setString(contador, (String) parametro);
                }
            }else if(parametro instanceof Integer){
                if(isfuncion){
                    sentenciaFuncion.setInt(contador, (Integer) parametro);
                }else {
                    sentencia.setInt(contador, (Integer) parametro);
                }
            }else if(parametro instanceof Double){
                if(isfuncion){
                    sentenciaFuncion.setDouble(contador, (Double) parametro);
                }else {
                    sentencia.setDouble(contador, (Double) parametro);
                }
            }else if(parametro instanceof Long){
                if(isfuncion){
                    sentenciaFuncion.setLong(contador, (Long) parametro);
                }else {
                    sentencia.setLong(contador, (Long) parametro);
                }
            }else if(parametro instanceof java.util.Date){
                if(isfuncion){
                    sentenciaFuncion.setDate(contador, new java.sql.Date(((java.util.Date) parametro).getTime()));
                }else {
                    sentencia.setDate(contador, new java.sql.Date(((java.util.Date) parametro).getTime()));
                }
            }else if(parametro instanceof LocalDate){
                java.util.Date date = Date.from(
                        ((LocalDate)parametro).atStartOfDay()
                                .atZone(ZoneId.systemDefault())
                                .toInstant());
                if(isfuncion){
                    sentenciaFuncion.setDate(contador, new java.sql.Date(((java.util.Date) date).getTime()));
                }else {
                    sentencia.setDate(contador, new java.sql.Date(((java.util.Date) date).getTime()));
                }
            }else if(parametro instanceof InputStream){
                if(isfuncion){
                    sentenciaFuncion.setBlob(contador, (InputStream) parametro);
                }else {
                    sentencia.setBlob(contador, (InputStream) parametro);
                }
            }else {
                if(isfuncion){
                    sentenciaFuncion.setObject(contador, parametro);
                }else {
                    sentencia.setObject(contador, parametro);
                }
            }
        }
    }

    public void commit() throws SQLException {
        if(conn.isClosed()){
            try {
                conectarDB();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        conn.commit();
    }
}
