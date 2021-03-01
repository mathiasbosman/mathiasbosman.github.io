package be.mathiasbosman.cv.api;

import be.mathiasbosman.cv.dto.PostContentDto;
import be.mathiasbosman.cv.dto.PostDto;
import be.mathiasbosman.cv.dto.UserDto;
import be.mathiasbosman.cv.oauth2.OAuth2Attribute;
import be.mathiasbosman.cv.oauth2.OAuth2Service;
import be.mathiasbosman.cv.service.PostService;
import be.mathiasbosman.cv.service.UserService;
import be.mathiasbosman.cv.util.WebUtils;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ServiceController {

  private final PostService postService;
  private final UserService userService;
  private final OAuth2Service oAuth2Service;

  public ServiceController(PostService postService, OAuth2Service oAuth2Service,
      UserService userService) {
    this.postService = postService;
    this.oAuth2Service = oAuth2Service;
    this.userService = userService;
  }

  /**
   * Get all none-deleted posts
   * @return List of PostDto
   */
  @GetMapping(value = "/public/posts")
  public List<PostDto> posts() {
    return postService.getPosts();
  }

  /**
   * Get all posts for certain user
   * @param userId The UUID of the user
   * @return List of PostDto
   */
  @GetMapping(value = "/public/posts/{userId}")
  public List<PostDto> postsByUsername(@PathVariable("userId") UUID userId) {
    return postService.getPosts(userId);
  }

  /**
   * Deletes a certain post
   * @param id The UUID of the post
   * @return PostDto of the deleted post
   */
  @DeleteMapping(value = "/post/{id}")
  public @ResponseBody
  PostDto deletePost(@PathVariable UUID id) {
    return postService.delete(id);
  }

  /**
   * Validates given post content
   * @param contentDto The content to validate
   * @return Result of the validation check. Either true or false
   */
  @PostMapping(value = "/validate")
  public @ResponseBody
  boolean validate(@RequestBody PostContentDto contentDto) {
    return postService.validate(contentDto);
  }

  /**
   * Save a post
   * @param contentDto The content to save
   * @return PostDto of the created post
   */
  @PostMapping(value = "/post")
  public @ResponseBody
  PostDto post(@RequestBody PostContentDto contentDto) {
    String email = oAuth2Service.getStringAttribute(WebUtils.token(), OAuth2Attribute.EMAIL);
    UserDto userDto = userService.getUserByEmail(email);
    return postService.post(contentDto, userDto.getUserId());
  }
}
