package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.get;

public class GetUserTest extends BaseTest {
	
	@Test
	public void getUserTest() {
		Map<String,String> queryParams = new HashMap<String,String>();
		//Map Of also can be used
		queryParams.put("name","amit");
		queryParams.put("status","active");
		
		Response actual_response = restClient.get("/public/v2/users", queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(actual_response.statusCode(), 200);
	}
	
	
	@Test
	public void getSingleUserTest() {
		Map<String,String> queryParams = new HashMap<String,String>();
			
		Response actual_response = restClient.get("/public/v2/users/7466255", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(actual_response.statusCode(), 200);
	}

}


