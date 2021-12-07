package pl.javastart.restassured.test.pet;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.Category;
import pl.javastart.restassured.pojo.Pet;
import pl.javastart.restassured.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTests {

  @BeforeClass
  public void setupConfiguration(){
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//Stosowanie statycznej konfiguracji uniemożliwia w większości przypadków poprawne wykonanie testów wielowątkowo!
// Statyczną konfigurację należy stosować z rozwagą!
    RestAssured.requestSpecification = new RequestSpecBuilder().setContentType("application/json").build();
    RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
  }


  @Test
  public void givenPetWhenPostPetThenPetIsCreatedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();

    given().body(pet)
            .when().post("/pet");
  }

  @Test
  public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
    given()
            .pathParam("petId", 1)
            .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}");
  }

  @Test
  public void givenExistingPetWhenUpdatePetNameThenPetIsChangedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();

    given().body(pet)
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet");

    pet.setName("Reksio");

    given().body(pet)
            .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/pet");
  }

  @Test
  public void givenExistingPetIdWhenDeletingPetThenIsDeletedTest() {

    Pet pet = Pet.builder()
            .id(123).category(Category.builder().id(1).name("dogs").build())
            .photoUrls(Collections.singletonList("http://photos.com/dog1.jpg"))
            .tags(Collections.singletonList(Tag.builder().id(1).name("dogs-category").build()))
            .status("available")
            .build();

    given().body(pet)
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet");

    given()
            .pathParam("petId", pet.getId())
            .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}");
  }

}
