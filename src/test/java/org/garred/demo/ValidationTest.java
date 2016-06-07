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
		final Employee employee = new Employee("legal name", 18);
		final Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		assertEquals(0, result.size());
	}

	@Test
	public void testNameTooLong() {
		final Employee employee = new Employee("this is a name that is too long", 18);
		final Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		final ConstraintViolation<Employee> violation = assertSingleViolation(result);
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("size must be between 0 and 12", violation.getMessage());
	}

	@Test
	public void testAgeTooYoung() {
		final Employee employee = new Employee("George", 17);
		final Set<ConstraintViolation<Employee>> result = validator.validate(employee);
		final ConstraintViolation<Employee> violation = assertSingleViolation(result);
		assertEquals("age", violation.getPropertyPath().toString());
		assertEquals("must be greater than or equal to 18", violation.getMessage());

	}
	
	

	private static ConstraintViolation<Employee> assertSingleViolation(Set<ConstraintViolation<Employee>> result) {
		assertEquals(1, result.size());
		return result.iterator().next();
	}

}
