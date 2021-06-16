package APITesting.com.org.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;

public class JsonServerRequest {

	// Get Request

	@Test

	public void test_01() {
		Response resp = given().when().get("http://localhost:3000/posts");

		System.out.println(resp.asString());

	}
	// Post Request

	@Test

	public void test_02() {
		Response resp = given().body("{ \"id\": \"2\", "
	            + "\"title\": \"hi Title\"," 
				+ "\"author\":\"sourabh\" } ").
				when().contentType(ContentType.JSON).
				post("http://localhost:3000/posts");

		System.out.println(resp.asString());
	}
}
