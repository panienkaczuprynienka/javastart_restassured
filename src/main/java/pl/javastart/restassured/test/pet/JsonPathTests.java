package pl.javastart.restassured.test.pet;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pl.javastart.restassured.enums.PetStatus;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class JsonPathTests {

  @Test
  public void givenPetWhenPostPetThenPetIsCreatedTest() {

    Pet pet = Pet.builder()
            .id(777).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status(PetStatus.SOLD.getStatus())
            .build();

    given().log().all().body(pet).contentType("application/json")
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().statusCode(200);

    JsonPath jsonPathResponse = given().log().method().log().uri()
            .pathParam("petId", pet.getId())
            .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
            .then().log().all().statusCode(200)
            .extract().jsonPath();

    String petName = jsonPathResponse.getString("name");
    String actualCategoryName = jsonPathResponse.getString("category.name");
    String tagName = jsonPathResponse.getString("tags[0].name");

    assertEquals(petName, pet.getName(), "Pet name");
    assertEquals(actualCategoryName, pet.getCategory().getName(), "Pet category name");
    assertEquals(tagName, pet.getTags().get(0).getName(), "Pet Tag name");
  }



}
