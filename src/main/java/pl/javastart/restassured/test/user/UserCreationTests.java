package pl.javastart.restassured.test.user;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import pl.javastart.restassured.pojo.User;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserCreationTests extends UserBaseTest {


  @Test
  public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder(); //Tworzymy instancję klasy RequestSpecBuilder
    requestSpecBuilder.setContentType(ContentType.JSON); // Dodajemy nagłówek ContentType
    RequestSpecification defaultRequestSpecification = requestSpecBuilder.build();

    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder(); //Tworzymy instancję klasy RequestSpecBuilder
    responseSpecBuilder.expectStatusCode(200); // Dodajemy nagłówek ContentType
    responseSpecBuilder.expectResponseTime(Matchers.lessThan(2000l), TimeUnit.MILLISECONDS);
    ResponseSpecification defaultResponseSpecification = responseSpecBuilder.build();

    User user = User.builder()
            .id(10).username("patkowicz").firstName("Patrycja").lastName("Żurnalska").email("patka@test.com")
            .password("13123@#123eqw").phone("+123456789").userStatus(123).build();

    given()
            .spec(defaultRequestSpecification)
            .body(user)
            .when().post("/user")
            .then()
            .spec(defaultResponseSpecification)
            .assertThat()
            .body("code", equalTo(200),
                    "type", equalTo("unknown"),
                    "message", equalTo("10"));

    given()
            .spec(defaultRequestSpecification)
            .pathParam("username", user.getUsername())
            .when()
            .get("/user/{username}")
            .then()
            .spec(defaultResponseSpecification)
            .assertThat().body("id", equalTo(10))
            .assertThat().body("username", equalTo("patkowicz"))
            .assertThat().body("firstName", equalTo("Patrycja"))
            .assertThat().body("lastName", equalTo("Żurnalska"))
            .assertThat().body("email", equalTo("patka@test.com"))
            .assertThat().body("password", equalTo("13123@#123eqw"))
            .assertThat().body("phone", equalTo("+123456789"))
            .assertThat().body("userStatus", equalTo(123))
            .assertThat().statusCode(200);

  }

}
