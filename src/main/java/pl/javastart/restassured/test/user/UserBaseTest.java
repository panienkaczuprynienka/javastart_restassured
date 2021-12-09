package pl.javastart.restassured.test.user;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class UserBaseTest {

  @Parameters({"baseUri", "basePath"})
  @BeforeClass
  public void setupConfiguration(@Optional("https://swaggerpetstore.przyklady.javastart.pl") String baseUri, @Optional("/v2") String basePath){
    RestAssured.baseURI = baseUri;
    RestAssured.basePath = basePath;
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }
}
