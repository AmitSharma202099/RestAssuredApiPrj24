package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateUserTest extends BaseTest {

    //POST
    @Test
    public void updateUserWithBuilderTest() {
        //POST :Create a User
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

        //GET :fetch the same User using the userId
        Response responseGet = restClient.get("/public/v2/users/"+userId,null	,null,AuthType.BEARER_TOKEN,ContentType.JSON);

        Assert.assertEquals(responseGet.statusCode(),200);
        Assert.assertEquals(responseGet.jsonPath().getString("id"),userId);
        Assert.assertEquals(responseGet.jsonPath().getString("name"),user.getName());
        Assert.assertEquals(responseGet.jsonPath().getString("email"),user.getEmail());
        Assert.assertEquals(responseGet.jsonPath().getString("gender"),user.getGender());
        Assert.assertEquals(responseGet.jsonPath().getString("status"),user.getStatus());

        //PUT update the same user using the userId
        //update user details using setter methods

        user.setName("xyz");
        user.setStatus("inactive");
        Response responsePut = restClient.put("/public/v2/users/"+userId,user,null,
                null,AuthType.BEARER_TOKEN,ContentType.JSON);


        Assert.assertEquals(actual_response.statusCode(), 201);
        Assert.assertEquals(responsePut.jsonPath().getString("id"),userId);
        Assert.assertEquals(responsePut.jsonPath().getString("name"),user.getName());
        Assert.assertEquals(responsePut.jsonPath().getString("status"),user.getStatus());

        //GET :fetch the same User using the userId after Put/Update
        Response responseAfterUpdateGet = restClient.get("/public/v2/users/"+userId,null	,null,AuthType.BEARER_TOKEN,ContentType.JSON);

        Assert.assertEquals(responseAfterUpdateGet.statusCode(),200);
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("id"),userId);
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("name"),user.getName());
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("email"),user.getEmail());
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("gender"),user.getGender());
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("status"),user.getStatus());
    }

}
