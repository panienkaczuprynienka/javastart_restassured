package pl.javastart.restassured.test.user;

import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.User;

import static io.restassured.RestAssured.given;

public class UserCreationTests {

  @Test
  public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {
    User user = User.builder()
            .id(10).username("patkowicz").firstName("Patrycja").lastName("Żurnalska").email("patka@test.com")
            .password("13123@#123eqw").phone("+123456789").userStatus(123).build();

    given().log().all()
            .contentType("application/json")
            .body(user)
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/user")
            .then().log().all().statusCode(200);

    given()
            .contentType("application/json")
            .pathParam("username", user.getUsername())
            .when()
            .get("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
            .then()
            .statusCode(200);
  }

}
