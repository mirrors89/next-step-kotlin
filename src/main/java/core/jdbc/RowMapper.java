package core.jdbc;

import java.sql.ResultSet;

@FunctionalInterface
public interface RowMapper {
    Object mapRow(ResultSet resultSet);
}
