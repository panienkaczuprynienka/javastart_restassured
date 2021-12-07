package pl.javastart.restassured.test.pet;

import org.testng.annotations.Test;
import pl.javastart.restassured.enums.PetStatus;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class QueryParamsTests {

  @Test
  public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest() {
    Pet pet = Pet.builder()
            .id(777).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status(PetStatus.SOLD.getStatus())
            .build();

    given()
            .log().all()
            .body(pet)
            .contentType("application/json")
            .when()
            .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
            .then()
            .log().all()
            .statusCode(200);



    Pet[] pets = given()
            .log().all()
            .contentType("application/json")
            .queryParam("status", PetStatus.SOLD.getStatus())
            .when()
            .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/findByStatus")
            .then()
            .log().all()
            .statusCode(200)
            .extract().as(Pet[].class);

    System.out.println("ZWIERZATKA TO " + Arrays.stream(pets).allMatch(p -> p.getStatus().equals(PetStatus.SOLD.getStatus())));
    List<String> soldPetsNAmes = Arrays.stream(pets).map(p -> p.getName()).collect(Collectors.toList());
    System.out.println(soldPetsNAmes);

  }
}
