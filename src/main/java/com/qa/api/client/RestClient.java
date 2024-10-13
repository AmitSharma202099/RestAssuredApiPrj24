package com.qa.api.client;

import java.util.Map;

import com.qa.api.constants.AuthTypes;
import com.qa.api.exception.FrameworkException;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;

public class RestClient 
{
	
	//define Response SPecs:
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);	
	private ResponseSpecification responseSpec401 = expect().statusCode(401);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec422 = expect().statusCode(422);
	private ResponseSpecification responseSpec500 = expect().statusCode(500);


	
	private String baseUrl= ConfigManager.get("baseUrl");
			
			private RequestSpecification setupRequest(AuthTypes authType,ContentType contentType) 
			{
				
				RequestSpecification  request = RestAssured.given()
													.baseUri(baseUrl)
													.contentType(contentType)
													.accept(contentType);
	
	switch (authType) {
				case BEARER_TOKEN:
					request.header("Authorization", "Bearer " + ConfigManager.get("bearerToken"));
					break;
				case OAUTH2:
					request.header("Authorization" , "Bearer " + generateOAuth2Token());
					break;
				case BASIC_AUTH:
					request.header("Authorization" , "Basic");
					break;
				case API_KEY:
					request.header("api-key" ,  ConfigManager.get("apiKey"));
					break;
				case NO_AUTH:
					System.out.println("Auth is not required");
					break;
				default:
					System.out.println("This Auth is not supported ...Please pass the right AuthType");
					//System.err.println();
					throw new FrameworkException("No AuthType Supported");
					//break;
					}	
		return request;
		
			}
	



			private String generateOAuth2Token() {
				return RestAssured.given()
							.formParam("client_id", ConfigManager.get("clientId"))
							.formParam("clientsecret", ConfigManager.get("clientId"))
							.formParam("granttype", ConfigManager.get("grantType"))
							.post(ConfigManager.get("tokenUrl"))
							.then()
							.extract()
							.path("access_token");


	
			}
			
//****************************************CRUD Methods******************************
			
 /**
  * This method is used to call the get APis			
  * @param endpoint
  * @param queryParams
  * @param pathParams
  * @param authType
  * @param contentType
  * @return will return 
  */
			
	public Response get(String endpoint, Map<String,String> queryParams,
			Map<String,String> pathParams,AuthTypes authType,
			ContentType contentType) {
		
		RequestSpecification req = setupRequest(authType,contentType);
		
		if (queryParams != null) {
			req.queryParams(pathParams);
			}
		if (pathParams != null) {
			req.pathParams(pathParams);
		}
		
		return req.get(endpoint).then().spec(responseSpec200).extract().response();
		}
			
			
			
}			
