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
@ContextConfiguration(locations = "classpath:test-validation-config.xml")
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
		Employee employee = new Employee("a name that is too long", 18);
		Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		assertEquals(1, result.size());
		
		ConstraintViolation<Employee> violation = result.iterator().next();
		assertEquals("name", violation.getPropertyPath().toString());
	}
}
