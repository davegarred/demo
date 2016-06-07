package org.garred.demo.migrations;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.garred.demo.domain.Employee;

import com.fasterxml.jackson.databind.ObjectMapper;

public class V2_2__InsertAge implements JdbcMigration {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void migrate(Connection connection) throws Exception {
		try (CallableStatement initialStatement = connection.prepareCall("SELECT name, data from demo.employee")) {
		    try (ResultSet rs = initialStatement.executeQuery()) {
		        while(rs.next()) {
		            final Employee employee = objectMapper.readValue(rs.getAsciiStream("data"), Employee.class);
		            try (PreparedStatement statement = connection.prepareStatement("UPDATE demo.employee SET age=? WHERE name=?")) {
		                statement.setInt(1, employee.age);
		                statement.setString(2, employee.name);
		                statement.execute();
		            }
		        }
		    }
		}
	}

}
