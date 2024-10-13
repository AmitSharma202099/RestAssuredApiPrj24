package com.qa.api.base;

import org.testng.annotations.BeforeMethod;

import com.qa.api.client.RestClient;

public class BaseTest {
	
	protected RestClient restClient;
	
	@BeforeMethod
	public void setup() {
		restClient = new RestClient();
	}

}
