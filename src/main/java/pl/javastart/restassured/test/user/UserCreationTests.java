package pl.javastart.restassured.test.user;

import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserCreationTests extends UserBaseTest {

  @Test
  public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {
    User user = User.builder()
            .id(10).username("patkowicz").firstName("Patrycja").lastName("Żurnalska").email("patka@test.com")
            .password("13123@#123eqw").phone("+123456789").userStatus(123).build();

    given()
            .contentType("application/json")
            .body(user)
            .when().post("/user")
            .then().statusCode(200)
            .assertThat()
            .body("code", equalTo(200),
                    "type", equalTo("unknown"),
                    "message", equalTo("10"));

    given()
            .contentType("application/json")
            .pathParam("username", user.getUsername())
            .when()
            .get("/user/{username}")
            .then()

            .assertThat().body("id", equalTo(10))
            .assertThat().body("username", equalTo("patkowicz"))
            .assertThat().body("firstName", equalTo("Patrycja"))
            .assertThat().body("lastName", equalTo("Żurnalska"))
            .assertThat().body("email", equalTo("patka@test.com"))
            .assertThat().body("password", equalTo("13123@#123eqw"))
            .assertThat().body("phone", equalTo("+123456789"))
            .assertThat().body("userStatus", equalTo(123))
            .assertThat().statusCode(200);

        ;
  }

}
