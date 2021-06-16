package APITesting.com.org.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;

public class WeathergetRequest {

	// Simple annotation test
	// Simple get request for getting wheather request by City name
	// Status Code : 200

	// @Test
	public void Test_01() {

		Response resp = when()
				.get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=673c5650a20311041c26d61291b186ae");

		System.out.println(resp.statusCode());
		Assert.assertEquals(resp.statusCode(), 200);
	}

	// Status Code : 401 and wrong key

	// @Test
	public void Test_02() {

		Response resp = when()
				.get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=673c5650a203110c26d61291b186ae");

		System.out.println(resp.statusCode());
		Assert.assertEquals(resp.statusCode(), 401);
	}

	@Test
	public void Test_03() {

		Response resp = given().param("q", "london").param("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=673c5650a20311041c26d61291b186ae");

		System.out.println(resp.statusCode());
		Assert.assertEquals(resp.statusCode(), 200);

		if (resp.statusCode() == 200) {
			System.out.println("Api is working fine");
		} else {
			System.out.println("not working fine");
		}

	}

	@Test
	public void Test_04() {

		given().param("q", "London").param("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather").then().assertThat().statusCode(200);
		System.out.println("test4");

	}

	@Test
	public void Test_05() {

		Response resp = given().param("q", "London").param("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather");

		System.out.println(resp.asString());
	}

	@Test
	public void Test_06() {

		Response resp = given().param("zip", "133302,in").param("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather");
		Assert.assertEquals(resp.getStatusCode(), 200);

		System.out.println(resp.asString());

	}

	@Test
	public void Test_07() {

		String WeatherReport = given().param("zip", "133302,in").param("appid", "673c5650a20311041c26d61291b186ae")
				.when().get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON)
				.extract().path("weather[0].description");

		System.out.println(" WeatherReport   " + WeatherReport);

	}

	@Test
	public void Test_08() {

		Response resp = given().param("zip", "133302,in").param("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather");

		String ActualWeatherReport = resp.then().contentType(ContentType.JSON).extract().path("weather[0].description");

		String expectedWeatherReport = "clear sky";

		if (ActualWeatherReport.equalsIgnoreCase(expectedWeatherReport)) {
			System.out.println("TestCases pass");
		} else {
			System.out.println("TestCases fail");
		}
	}

	@Test
	public void test_09() {

		Response resp = given().parameter("id", "2172797").parameter("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather");

		String reportbyID = resp.then().contentType(ContentType.JSON).extract().path("weather[0].description");

		System.out.println("weather description by ID : " + reportbyID);

		String lon = String.valueOf(resp.then().contentType(ContentType.JSON).extract().path("coord.lon"));

		System.out.println("longitude is : " + lon);

		String lat = String.valueOf(resp.then().contentType(ContentType.JSON).extract().path("coord.lat"));

		System.out.println("latitude is : " + lat);

		String reportbyCoordinates = given().parameter("lat", lat).parameter("lon", lon)
				.parameter("appid", "673c5650a20311041c26d61291b186ae").when()
				.get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON).extract()
				.path("weather[0].description");

		System.out.println("report by coordinates : " + reportbyCoordinates);

		Assert.assertEquals(reportbyID, reportbyCoordinates);

	}
}
