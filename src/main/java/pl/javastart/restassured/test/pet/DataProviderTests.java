package pl.javastart.restassured.test.pet;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.javastart.restassured.dataProviders.RemoteDataProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class DataProviderTests {

  @DataProvider(name = "incorrectPetIds")
  public Object[][] localDataProvider() {
    return new Object[][]{
            {"0", "Pet not found"},
            {"aaa", "java.lang.NumberFormatException: For input string:"},
            {"!!!", "java.lang.NumberFormatException: For input string:"}
    };
  }

  @BeforeMethod
  public void beforeMethod() {
    RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
    RestAssured.basePath = "v2";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  // local provider z tej samej klasy
  @Test(dataProvider = "incorrectPetIds")
  public void givenNonExistingPetIdWhenGetPetThenPetNotFoundTest(String petId, String message) {
    given().when().get("pet/{param}", 0).then().statusCode(404);
  }

  //remote provider z osobnej klasy
  @Test(dataProviderClass = RemoteDataProvider.class, dataProvider = "remoteDataProvider")
  public void givenNonExistingPetIdWhenGetPetThenPetNotFoundWithRemoteDataProviderTest(String petId, String message) {
    given()
            .when().get("pet/{param}", petId)
            .then().statusCode(404).assertThat().body("message", containsString(message));
  }

}
