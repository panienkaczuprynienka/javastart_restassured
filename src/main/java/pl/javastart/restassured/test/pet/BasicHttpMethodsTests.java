package pl.javastart.restassured.test.pet;

import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTests {

  @Test
  public void givenPetWhenPostPetThenPetIsCreatedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();

    given().log().all().body(pet).contentType("application/json")
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().statusCode(200);
  }

  @Test
  public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
    given().log().method().log().uri()
            .pathParam("petId", 1)
            .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
            .then().log().all().statusCode(200);
  }

  @Test
  public void givenExistingPetWhenUpdatePetNameThenPetIsChangedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();

    given().log().all().body(pet).contentType("application/json")
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().statusCode(200);

    pet.setName("Reksio");

    given().log().all().body(pet).contentType("application/json")
            .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().statusCode(200);
  }

  @Test
  public void givenExistingPetIdWhenDeletingPetThenIsDeletedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();

    given().log().all().body(pet).contentType("application/json")
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().statusCode(200);

    given().log().all().contentType("application/json")
            .pathParam("petId", pet.getId())
            .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
            .then().log().all().statusCode(200);
  }

}
