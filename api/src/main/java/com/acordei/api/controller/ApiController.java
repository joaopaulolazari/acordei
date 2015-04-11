package com.acordei.api.controller;

import com.acordei.api.domain.Sample;
import com.acordei.api.service.SampleService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/api", description = "Api operations")
public class ApiController {
	@Autowired
	private SampleService service;

    @RequestMapping(value = "/api/sample", method = RequestMethod.GET)
    public @ResponseBody Sample sample() {
    	return service.sample();
    }
}
