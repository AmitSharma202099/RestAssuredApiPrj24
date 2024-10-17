package com.qa.api.tests;

import java.io.File;

import com.qa.api.utils.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static com.qa.api.utils.StringUtility.*;

public class CreateUserTest extends BaseTest {
@Test(enabled = false)	
	public void createUserTest() {
		
		User user = new User("Amit", "amit13@gmail.com","male","active");
		
		Response actual_response = restClient.post("/public/v2/users", user,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(actual_response.statusCode(), 201);
	}
//POST
@Test
	public void createUserWithBuilderTest() {
	//POST
		User  user = User.builder()
						.name(StringUtility.getName())
						.email(StringUtility.getRandonEmailId())
						.gender("female")
						.status("active")
						.build();
		
	Response actual_response = restClient.post("/public/v2/users", user,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
	Assert.assertEquals(actual_response.statusCode(), 201);
	//fetch userId:
	String userId= actual_response.jsonPath().getString("id");
	System.out.println("UserId is :" + userId);

	//GET
	Response responseGet = restClient.get("/public/v2/users/"+userId,null	,null,AuthType.BEARER_TOKEN,ContentType.JSON);

	Assert.assertEquals(responseGet.statusCode(),200);
	Assert.assertEquals(responseGet.jsonPath().getString("id"),userId);
	Assert.assertEquals(responseGet.jsonPath().getString("name"),user.getName());
	Assert.assertEquals(responseGet.jsonPath().getString("email"),user.getEmail());
	Assert.assertEquals(responseGet.jsonPath().getString("gender"),user.getGender());
	Assert.assertEquals(responseGet.jsonPath().getString("status"),user.getStatus());




}


@Test(enabled = false)
public void createUserWithJsonFileTest() {
	
File userJsonFile = new File("src\\test\\resources\\jsons\\user.json");
					

Response actual_response = restClient.post("/public/v2/users", userJsonFile,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
Assert.assertEquals(actual_response.statusCode(), 201);
}



}
