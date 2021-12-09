package pl.javastart.restassured.test.others;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pl.javastart.restassured.listeners.MyListener;

import static io.restassured.RestAssured.given;

@Listeners(MyListener.class)
public class ListenerTests {

  @BeforeClass
  public void beforeMethod() {
    RestAssured.baseURI = "https://google.pl";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @Test
  public void successGoogleTest() {
    given().when().get().then().statusCode(200);
  }

  @Test
  public void skipGoogleTest(){
    throw new SkipException("This test is skipped");
  }

  @Test
  public void failGoogleTest() {
    given().when().get("/fail").then().statusCode(200);
  }

}
