package com.qa.contacts.api.tests;

import org.testng.annotations.BeforeMethod;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactCredential;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactTest extends BaseTest{
	
	@BeforeMethod
	public void getToken() {
		
		ContactCredential cred = 	ContactCredential.builder()
			.email(null)
			.password(null)
			.build();
		
		Response response = restClient.get("/users/login",null,null,AuthType.NO_AUTH,ContentType.JSON);
		String tokenId = response.jsonPath().getString("token");
		System.out.println(tokenId);
		
		ConfigManager.set()
.
		
	}

}
