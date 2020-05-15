package com.gaoxiaobang.community.common.typeHandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间类型转换器，将当时时间转换成UTC时间戳保存到数据库，取出的时候将UTC时间戳转换成当地时间
 */
public class TimestampTypeHandler implements TypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(parameter.getTime());
        int i1 = calendar.get(Calendar.ZONE_OFFSET);//获取当前时间的时间偏移量
        int i11 = calendar.get(Calendar.DST_OFFSET);//获取夏令时差
        calendar.add(Calendar.MILLISECOND,-(i1+i11));//减去时间差
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        ps.setTimestamp(i,timestamp);
    }


    @Override
    public Date getResult(ResultSet rs, String columnName) throws SQLException {
        long time = rs.getTimestamp(columnName).getTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date(time));
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
        long time = rs.getTimestamp(columnIndex).getTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date(time));
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

    @Override
    public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
        long time = cs.getTimestamp(columnIndex).getTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date(time));
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }
}
