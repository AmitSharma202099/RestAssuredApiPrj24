package com.qa.api.client;

import java.io.File;
import java.util.Map;

import com.qa.api.constants.AuthType;
import com.qa.api.exception.FrameworkException;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

//Press Ctro + O to get complete view 

public class RestClient {

	// define Response SPecs:
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200),equalTo(404)));
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec401 = expect().statusCode(401);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec422 = expect().statusCode(422);
	private ResponseSpecification responseSpec500 = expect().statusCode(500);

	private String baseUrl = ConfigManager.get("baseUrl");

	private RequestSpecification setupRequest(AuthType authType, ContentType contentType) {

		RequestSpecification request = RestAssured.given().log().all()
							.baseUri(baseUrl)
								.contentType(contentType)
									.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("bearerToken"));
			break;
		case OAUTH2:
			request.header("Authorization", "Bearer " + generateOAuth2Token());
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic");
			break;
		case API_KEY:
			request.header("api-key", ConfigManager.get("apiKey"));
			break;
		case NO_AUTH:
			System.out.println("Auth is not required");
			break;
		default:
			System.out.println("This Auth is not supported ...Please pass the right AuthType");
			// System.err.println();
			throw new FrameworkException("No AuthType Supported");
		// break;
		}
		return request;

	}

	private String generateOAuth2Token() {
		return RestAssured.given().formParam("client_id", ConfigManager.get("clientId"))
				.formParam("clientsecret", ConfigManager.get("clientId"))
				.formParam("granttype", ConfigManager.get("grantType")).post(ConfigManager.get("tokenUrl")).then()
				.extract().path("access_token");

	}

//****************************************CRUD Methods******************************

	/**
	 * This method is used to call the get APi's
	 * @author amits
	 * @param endpoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return will return get api response
	 */
//GET
	public Response get(String endpoint, Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthandContentType(authType,contentType);
		applyParams(request,queryParams,pathParams);

		Response  response = request.get(endpoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
	}
//POST	
	
	/**
	 * This method is used to call the post APi's 
	 * @author amits
	 * @param <T>
	 * @param endpoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return will return post api responce
	 */
	
	public <T>Response post(String endpoint,T body , Map<String,String> queryParams,
			Map<String,String> pathParams,AuthType authType,ContentType contentType) {
		
		RequestSpecification request = setUpAuthandContentType(authType,contentType);
		applyParams(request,queryParams,pathParams);
		
		Response response = request.body(body).post(endpoint).then().spec(responseSpec201).extract().response();
		response.prettyPrint();
		return response;	
	}
//PUT	
	/**
	 * This method is used to call the put APi's 
	 * @param <T>
	 * @param endpoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @returnwill return post api responce
	 * @author amits
	 */
	public <T>Response put(String endpoint,T body , Map<String,String> queryParams,
			Map<String,String> pathParams,AuthType authType,ContentType contentType) {
		
		RequestSpecification request = setUpAuthandContentType(authType,contentType);
		applyParams(request,queryParams,pathParams);
		
		Response response = request.body(body).put(endpoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;	
	}
	
//PATCH
	/**
	 * This method is used to call the patch APi's
	 * @param <T>
	 * @param endpoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return post api responce
	 * @author amits
	 */
	public <T>Response patch(String endpoint,T body , Map<String,String> queryParams,
			Map<String,String> pathParams,AuthType authType,ContentType contentType) {
		
		RequestSpecification request = setUpAuthandContentType(authType,contentType);
		applyParams(request,queryParams,pathParams);
		
		Response response = request.body(body).patch(endpoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;	
	}
//DELETE
/**
 * This method is used to call the delete APi'
 * @param endpoint
 * @param queryParams
 * @param pathParams
 * @param authType
 * @param contentType
 * @return post api responce
 * @author amits
 */
		public Response delete(String endpoint, Map<String, String> queryParams, Map<String, String> pathParams,
				AuthType authType, ContentType contentType) {

			RequestSpecification request = setUpAuthandContentType(authType,contentType);
			applyParams(request,queryParams,pathParams);

			Response  response = request.delete(endpoint).then().spec(responseSpec204).extract().response();
			response.prettyPrint();
			return response;	
		}
	/**
	 * This method is used to call the post APi's for Json File Body 
	 * @author amits
	 * @param <T>
	 * @param endpoint
	 * @param file
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return will return post api responce
	 */
	public <T>Response post(String endpoint,File file , Map<String,String> queryParams,
			Map<String,String> pathParams,AuthType authType,ContentType contentType) {
		
		RequestSpecification request = setUpAuthandContentType(authType,contentType);
		applyParams(request,queryParams,pathParams);
		
		Response response = request.body(file).post(endpoint).then().spec(responseSpec201).extract().response();
		response.prettyPrint();
		return response;	
	}
	
	private RequestSpecification setUpAuthandContentType(AuthType authType,ContentType contentType) {
		return setupRequest(authType, contentType);

	}
	
	private void applyParams(RequestSpecification request,Map<String, String> queryParams, Map<String, String> pathParams) {
		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		if (pathParams != null) {
			request.pathParams(pathParams);
		}
	}
	
}


