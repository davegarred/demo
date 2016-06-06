package org.garred.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.garred.demo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueNameValidator implements ConstraintValidator<UniqueName,String> {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
    public void initialize(UniqueName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if(name == null) {
            return true;
        }
        if(this.employeeRepository.find(name) == null) {
        	return true;
        }
        return false;
    }
    
}
