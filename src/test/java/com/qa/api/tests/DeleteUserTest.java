package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest {
    //POST
    @Test
    public void deleteUserWithBuilderTest() {
        //1.POST :Create a User
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

        //2.GET :fetch the same User using the userId
        Response responseGet = restClient.get("/public/v2/users/"+userId,null	,null,AuthType.BEARER_TOKEN,ContentType.JSON);

        Assert.assertEquals(responseGet.statusCode(),200);
        Assert.assertEquals(responseGet.jsonPath().getString("id"),userId);
        Assert.assertEquals(responseGet.jsonPath().getString("name"),user.getName());
        Assert.assertEquals(responseGet.jsonPath().getString("email"),user.getEmail());
        Assert.assertEquals(responseGet.jsonPath().getString("gender"),user.getGender());
        Assert.assertEquals(responseGet.jsonPath().getString("status"),user.getStatus());


        //3.DELETE :fetch the same User using the userId after Patch
        Response responseDelete = restClient.delete("/public/v2/users/"+userId,null,null,
                AuthType.BEARER_TOKEN,ContentType.JSON);

        Assert.assertEquals(responseDelete.statusCode(),204);


        //4.GET :fetch the same User using the userId after Delete
        Response responseGetAfterDelete = restClient.get("/public/v2/users/"+userId,null,null,
                AuthType.BEARER_TOKEN,ContentType.JSON);

        Assert.assertEquals(responseGetAfterDelete.statusCode(),404);
        Assert.assertEquals(responseGetAfterDelete.jsonPath().getString("message"),"Resource not found");

    }


}
