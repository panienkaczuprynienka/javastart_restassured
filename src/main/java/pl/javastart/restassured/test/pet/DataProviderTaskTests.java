package pl.javastart.restassured.test.pet;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.javastart.restassured.enums.PetStatus;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class DataProviderTaskTests {

  @DataProvider(name = "petStatuses")
  public Object[][] localDataProvider() {
    return new Object[][]{
            {PetStatus.SOLD.getStatus()},
            {PetStatus.PENDING.getStatus()},
            {PetStatus.AVAILABLE.getStatus()}
    };
  }

  @BeforeMethod
  public void beforeMethod() {
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @Test(dataProvider = "petStatuses")
  public void givenPetStatusWhenPetIsCreatedThenPetWithPetStatusIsAvailableTest(String status) {
    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogg").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status(status)
            .build();

    String actualPetStatus = given()
            .contentType(ContentType.JSON)
            .body(pet)
            .when()
            .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then()
            .statusCode(200)
            .extract().jsonPath().getString("status");
    assertEquals(actualPetStatus, pet.getStatus(), "Pet status is not as expected");


  }
}
