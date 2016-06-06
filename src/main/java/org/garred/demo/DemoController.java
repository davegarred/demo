package org.garred.demo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.garred.demo.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class DemoController {

	private final EmployeeRepository employeeRepository;
    
	public DemoController(EmployeeRepository employeeRepository) {
	this.employeeRepository = employeeRepository;
}

	@RequestMapping(value = "/data", method = GET)
	@ResponseBody
	public Object allEmployees() throws JsonProcessingException {
		return this.employeeRepository.findAll();
	}

	@RequestMapping(value = "/add", method = PUT)
	@ResponseBody
	public Object addEmployee(@RequestBody @Valid Employee employee) throws JsonProcessingException {
		this.employeeRepository.insert(employee);
		return this.employeeRepository.findAll();
	}

}