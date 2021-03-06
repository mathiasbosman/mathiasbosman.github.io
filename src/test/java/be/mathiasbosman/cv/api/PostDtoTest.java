package be.mathiasbosman.cv.api;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.cv.dto.PostDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PostDtoTest extends AbstractJsonMapperTest {

  @Test
  @Override
  void serializeDeserializeTest() throws JsonProcessingException {
    PostDto post = new PostDto(null,
        "subject",
        "excerpt",
        "body",
        null,
        LocalDateTime.of(2000, 2, 3, 10, 20, 30),
        LocalDateTime.of(2000, 5, 4, 10, 0, 0));
    String json = jsonMapper().writeValueAsString(post);
    assertThat(json).isEqualTo(
        "{\"content\":{\"body\":\"body\",\"excerpt\":\"excerpt\",\"subject\":\"subject\"},\"id\":null,\"postDate\":\"2000-02-03T10:20:30\",\"poster\":null,\"updated\":\"2000-05-04T10:00:00\"}");
  }
}
