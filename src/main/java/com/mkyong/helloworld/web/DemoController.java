package com.mkyong.helloworld.web;

import static java.util.Arrays.asList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DemoController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> values = new ArrayList<>(asList("string 1", "string 2", "and another"));
    
	@RequestMapping(value = "/data", method = GET)
	@ResponseBody
	public String index() throws JsonProcessingException {

		return objectMapper.writeValueAsString(values);
	}

}