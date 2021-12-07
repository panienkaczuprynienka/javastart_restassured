package pl.javastart.restassured.test.user;

import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.User;

import static io.restassured.RestAssured.given;

public class UserUpdateTests {

  @Test
  public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest() {

    User user = User.builder()
            .id(10).username("EwelinaEwelka").firstName("Ewelina").lastName("Wicherska").email("ewelka@test.com")
            .password("qwerty").phone("+123456789").userStatus(12).build();

    given().log().all()
            .contentType("application/json")
            .body(user)
            .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/user")
            .then().log().all().statusCode(200);

    user.setFirstName("Ewa");
    user.setLastName("Wicher");


    given()
            .contentType("application/json")
            .pathParam("username", user.getUsername())
            .body(user)
            .when()
            .put("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
            .then()
            .statusCode(200);

    given()
            .contentType("application/json")
            .pathParam("username", user.getUsername())
            .when()
            .get("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
            .then()
            .statusCode(200);

  }


}
