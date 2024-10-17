package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PatchUserTest extends BaseTest {

    //POST
    @Test
    public void patchUserWithBuilderTest() {
        //POST :Create a User
        User user = User.builder()
                .name(StringUtility.getName())
                .email(StringUtility.getRandonEmailId())
                .gender("female")
                .status("inactive")
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

        //PATCH update the same user using the userId
        //update user details using setter methods

        user.setName("VSA");
        user.setGender("male");
        user.setStatus("active");
        Response responsePatch = restClient.patch("/public/v2/users/"+userId,user,null,
                null,AuthType.BEARER_TOKEN,ContentType.JSON);


        Assert.assertEquals(actual_response.statusCode(), 201);
        Assert.assertEquals(responsePatch.jsonPath().getString("id"),userId);
        Assert.assertEquals(responsePatch.jsonPath().getString("name"),user.getName());
        Assert.assertEquals(responsePatch.jsonPath().getString("status"),user.getStatus());
        //Assert.assertEquals(responsePatch.jsonPath().getString("male"),user.getGender());

        //GET :fetch the same User using the userId after Patch
        Response responseAfterUpdateGet = restClient.get("/public/v2/users/"+userId,null	,null,AuthType.BEARER_TOKEN,ContentType.JSON);

        Assert.assertEquals(responseAfterUpdateGet.statusCode(),200);
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("id"),userId);
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("name"),user.getName());
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("email"),user.getEmail());
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("gender"),user.getGender());
        Assert.assertEquals(responseAfterUpdateGet.jsonPath().getString("status"),user.getStatus());
    }

}
