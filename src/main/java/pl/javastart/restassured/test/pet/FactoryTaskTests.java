package pl.javastart.restassured.test.pet;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import pl.javastart.restassured.enums.PetStatus;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class FactoryTaskTests {

  private String petName;
  private PetStatus status;

  FactoryTaskTests(String petName, PetStatus status) {
    this.petName = petName;
    this.status = status;
  }

  @BeforeMethod
  public void beforeMethod() {
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }


  @Test
  public void givenPetStatusAndNameWhenPetIsCreatedThenPetWithPetStatusAndNameIsAvailableTest() {
    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name(petName).build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status(status.getStatus())
            .build();

    Pet petCreated = given()
            .contentType(ContentType.JSON)
            .body(pet)
            .when()
            .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then()
            .statusCode(200)
            .extract().as(Pet.class);

    assertEquals(petCreated.getStatus(), status.getStatus(), "Pet status is not as expected");
    assertEquals(petCreated.getCategory().getName(), petName, String.format("Pet name is not as expected, from response {0}, from request {1}", petCreated.getName(), petName));

  }

  @Factory
  public static Object[] factoryMethod() {
    return new Object[]{
            new FactoryTaskTests("Reksio soldić", PetStatus.SOLD),
            new FactoryTaskTests("Burek dostepny", PetStatus.AVAILABLE),
            new FactoryTaskTests("Saba oczekująca", PetStatus.PENDING)};
  }
}
