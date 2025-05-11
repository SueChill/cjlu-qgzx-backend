package com.cjlu.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串列表类型处理器
 * 用于处理数据库中的字符串转为Java中的List<String>
 */
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 将列表转换为逗号分隔的字符串
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < parameter.size(); j++) {
            sb.append(parameter.get(j));
            if (j < parameter.size() - 1) {
                sb.append(",");
            }
        }
        ps.setString(i, sb.toString());
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return convertToList(columnValue);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return convertToList(columnValue);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return convertToList(columnValue);
    }

    private List<String> convertToList(String columnValue) {
        if (columnValue == null) {
            return null;
        }
        return Arrays.asList(columnValue.split(","));
    }
}