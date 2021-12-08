package pl.javastart.restassured.test.others;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BadSslTest {

  @Test
  public void sslCertExpiredTest() {
    given().relaxedHTTPSValidation().when().get("https://expired.badssl.com/").then().statusCode(200);
  }
// mozna tez dodawac to w request spec
}
