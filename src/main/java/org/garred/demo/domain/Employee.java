package org.garred.demo.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

	@NotNull
	@Size(max = 12)
//	@UniqueName
	public final String name;
	
	@Min(18)
	@Max(65)
	public final int age;
	
	@JsonCreator
	public Employee(@JsonProperty("name") String name, @JsonProperty("age") int age) {
		this.name = name;
		this.age = age;
	}
	
}
