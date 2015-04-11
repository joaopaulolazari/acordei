package com.acordei.api.service;

import com.acordei.api.domain.Sample;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

	public Sample sample() {
		return new Sample("sample");
	}

}
