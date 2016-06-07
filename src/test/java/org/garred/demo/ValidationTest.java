package org.garred.demo;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.garred.demo.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/validation-config.xml", "classpath:spring/persistence-config.xml"})
public class ValidationTest {

    @Autowired
    public Validator validator;
    
	@Test
	public void testValid() {
		Employee employee = new Employee("legal name", 18);
		Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		assertEquals(0, result.size());
	}

	@Test
	public void testNameTooLong() {
		Employee employee = new Employee("this is a name that is too long", 18);
		Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		assertSingleViolation(result, "name", "size must be between 0 and 12");
	}

	@Test
	public void testAgeTooYoung() {
		Employee employee = new Employee("George", 17);
		Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		assertSingleViolation(result, "age", "must be greater than or equal to 18");
	}
	
	

	private static void assertSingleViolation(Set<ConstraintViolation<Employee>> result, String path, String message) {
		assertEquals(1, result.size());
		ConstraintViolation<Employee> violation = result.iterator().next();
		
		assertEquals(path, violation.getPropertyPath().toString());
		assertEquals(message, violation.getMessage());
	}

}
