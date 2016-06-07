package org.garred.demo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.garred.demo.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/persistence-config.xml")
public class RepositoryTest {

    @Autowired
    public EmployeeRepository repository;
    
	@Test
	public void testFindOne() {
		Employee joe = repository.findByName("Joe");
		assertEquals("Joe", joe.name);
		assertEquals(36, joe.age);
	}

	@Test
	public void testInsertAndFindAll() {
		Collection<Employee> employees = repository.findAll();
		assertEquals(1, employees.size());
		
		Employee employee = new Employee("Ally", 18);
		repository.insert(employee);
		
		employees = repository.findAll();
		assertEquals(2, employees.size());

		Employee ally = repository.findByName("Ally");
		assertEquals("Ally", ally.name);
		assertEquals(18, ally.age);
	}

	

}
