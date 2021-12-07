package pl.javastart.restassured.test.pet;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FirstRestAssuredAutomatedTests {

  @Test
  public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
    given()
            .pathParam("petId", 1)
            .log().uri()
            .log().method()
            .when()
            .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
            .then()
            .log().all()
            .statusCode(200);
  }

}
