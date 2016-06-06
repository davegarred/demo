package org.garred.demo;

import static java.sql.Types.VARCHAR;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

import javax.sql.DataSource;

import org.garred.demo.domain.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeRepository {

	private static final String FIND_ALL = "SELECT * FROM demo.employee";
	private static final String FIND_ONE = "SELECT data FROM demo.employee WHERE name= ?";
	private static final String INSERT_STATEMENT = "INSERT INTO demo.employee(name, data) VALUES (?, ?)";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final ResultSetExtractor<Employee> resultSetExtractor = new ResultSetExtractor<Employee>() {
		@Override
		public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(!rs.next()) {
				return null;
			}
			return employee(rs);
		}
	};
	private static final RowMapper<Employee> ROW_MAPPER = new RowMapper<Employee>() {
		@Override
		public Employee mapRow(ResultSet rs, int arg1) throws SQLException {
			return employee(rs);
		}
	};
	
	private final JdbcTemplate jdbcTemplate;
	public EmployeeRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Employee find(String name) {
		return this.jdbcTemplate.query(findOneStatement(name), resultSetExtractor);
	}

	public Collection<Employee> findAll() {
		return this.jdbcTemplate.query(FIND_ALL, ROW_MAPPER);
	}
	
	public void insert(Employee employee) {
		this.jdbcTemplate.update(insertOneStatement(employee));
	}
	
	
	private static Employee employee(ResultSet rs) throws SQLException {
		try {
			return MAPPER.readValue(rs.getAsciiStream("data"), Employee.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static PreparedStatementCreator findOneStatement(String name) {
		PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(FIND_ONE, VARCHAR);
		return preparedStatementCreatorFactory.newPreparedStatementCreator(asList(name));
	}
	
	private static PreparedStatementCreator insertOneStatement(Employee employee) {
		try {
			String serializedEmployee = MAPPER.writeValueAsString(employee);
			PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(INSERT_STATEMENT, VARCHAR, Types.CLOB);
			return preparedStatementCreatorFactory.newPreparedStatementCreator(asList(employee.name, serializedEmployee));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
