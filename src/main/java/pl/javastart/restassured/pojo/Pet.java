package pl.javastart.restassured.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonDeserialize(as= Pet.class)
public class Pet {

  private Integer id;
  private Category category;
  private String name;
  private List<String> photoUrls;
  private List<Tag> tags ;
  private String status;

}
