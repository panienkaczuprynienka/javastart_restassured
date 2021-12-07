package pl.javastart.restassured.test.pet;

import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class SerializationAndDeserializationTests {

  @Test
  public void givenPetWhenPostPetThenPetIsCreatedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();


    Pet actualPet = given().log().all().body(pet).contentType("application/json")
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then().log().all().statusCode(200)
            .extract().as(Pet.class);

    assertEquals(actualPet.getId(), pet.getId(), "Pet id");
    assertEquals(actualPet.getCategory().getId(), pet.getCategory().getId(), "Category id");
    assertEquals(actualPet.getCategory().getName(), pet.getCategory().getName(), "Category name");
    assertEquals(actualPet.getPhotoUrls().get(0), pet.getPhotoUrls().get(0), "Photo URL");
    assertEquals(actualPet.getTags().get(0).getId(), pet.getTags().get(0).getId(), "Pet tag id");
    assertEquals(actualPet.getTags().get(0).getName(), pet.getTags().get(0).getName(), "Pet tag name");
    assertEquals(actualPet.getStatus(), pet.getStatus(), "Pet status");

    Pet getedPet = given().log().all().body(pet).contentType("application/json")
            .pathParam("petId", actualPet.getId())
            .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
            .then().log().all().statusCode(200)
            .extract().as(Pet.class);

    assertEquals(actualPet, getedPet);
  }


}
