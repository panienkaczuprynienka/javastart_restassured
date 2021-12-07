package pl.javastart.restassured.test.pet;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pl.javastart.restassured.enums.PetStatus;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class GettingHeadersAndCookiesTests {

  @Test
  public void givenPetWhenPostPetThenPetIsCreatedTest() {

    Pet pet = Pet.builder()
            .id(1).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status(PetStatus.SOLD.getStatus())
            .build();



    Response response = given().log().all().body(pet).contentType("application/json")
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().extract().response();

    int statusCode = response.getStatusCode();
    String statusLine = response.getStatusLine();
    Headers responseHeaders = response.getHeaders();
    Map<String, String> cookies = response.getCookies();

    assertEquals(statusLine, "HTTP/1.1 200 OK", "Status line");
    //assertEquals(statusCode, 200, "Status code");
    assertNotNull(responseHeaders.get("Date"));
    assertEquals(responseHeaders.get("Content-Type").getValue(), "application/json", "Content type header");
    assertEquals(responseHeaders.get("Server").getValue(), "nginx/1.10.3", "Server header");
    assertTrue(cookies.isEmpty(), "Cookies are empty");
  }



}
