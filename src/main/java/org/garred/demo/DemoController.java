package org.garred.demo;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.garred.demo.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

	private final EmployeeRepository employeeRepository;
    
	public DemoController(EmployeeRepository employeeRepository) {
	this.employeeRepository = employeeRepository;
}

	@RequestMapping(value = "/data", method = GET)
	@ResponseBody
	public Object allEmployees() {
		return this.employeeRepository.findAll();
	}

	@RequestMapping(value = "/data", method = PUT, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object addEmployee(@RequestBody @Valid Employee employee) {
		this.employeeRepository.insert(employee);
		return this.employeeRepository.findAll();
	}

}