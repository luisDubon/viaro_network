package com.viaro.network.doSoftware.Mapping;


import com.viaro.network.doSoftware.bean.Estadistica;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadisticaRowMapper implements RowMapper<Estadistica> {

    @Override
    public Estadistica mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Estadistica(rs.getString(1), rs.getString(2), rs.getString(3),
                rs.getString(4));
    }
}
