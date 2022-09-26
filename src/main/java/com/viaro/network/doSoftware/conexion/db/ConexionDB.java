package com.viaro.network.doSoftware.conexion.db;

import com.mysql.cj.MysqlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ConexionDB {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <E> List<E> getList(String sql, RowMapper<E> mapper) throws Exception{
        return jdbcTemplate.query(sql, mapper);
    }

    public <E> List<E> getList(String sql, RowMapper<E> mapper, Object... params) throws Exception{
        return jdbcTemplate.query(sql,mapper,params);
    }

    public <E> E getItem(String sql, RowMapper<E> mapper, Object... params) throws Exception{
            return jdbcTemplate.queryForObject(sql, mapper, params);
    }

    public void call(String dml, Object... params) throws Exception{
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement sentencia = con.prepareStatement(dml);
                if(params != null){
                    for(int i = 0; i< params.length; i++){
                        Object parametro = params[i];
                        int contador = i+1;
                        if(parametro instanceof String){
                                sentencia.setString(contador, (String) parametro);
                        }else if(parametro instanceof Integer){
                                sentencia.setInt(contador, (Integer) parametro);
                        }else if(parametro instanceof Double){
                                sentencia.setDouble(contador, (Double) parametro);
                        }else if(parametro instanceof Long){
                                sentencia.setLong(contador, (Long) parametro);
                        }else if(parametro instanceof java.util.Date){
                                sentencia.setDate(contador, new java.sql.Date(((java.util.Date) parametro).getTime()));
                        }else if(parametro instanceof LocalDate){
                            java.util.Date date = java.sql.Date.from(
                                    ((LocalDate)parametro).atStartOfDay()
                                            .atZone(ZoneId.systemDefault())
                                            .toInstant());
                                sentencia.setDate(contador, new java.sql.Date(((java.util.Date) date).getTime()));
                        }else if(parametro instanceof InputStream){
                                sentencia.setBlob(contador, (InputStream) parametro);
                        }else {
                                sentencia.setObject(contador, parametro);
                        }
                    }
                }else {
                    sentencia.setNull(1,MysqlType.FIELD_TYPE_NULL);
                }
                return sentencia;
            }
        });
    }

}
