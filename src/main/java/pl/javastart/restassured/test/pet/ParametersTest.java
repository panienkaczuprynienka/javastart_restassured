package pl.javastart.restassured.test.pet;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class ParametersTest {
  @BeforeMethod
  public void beforeMethod() {
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @Parameters({"petId", "expectedMessage"})
  @Test
  public void givenNonExistingPetIdWhenGetPetThenPetNotFoundTest(@Optional("0") String petId,
                                                                 @Optional("Pet not found") String expectedMessage) {
    given()
            .when().get("pet/{param}", petId)
            .then().statusCode(404)
            .body("message", containsString(expectedMessage));
      }


}
