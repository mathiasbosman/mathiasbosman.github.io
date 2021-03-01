package be.mathiasbosman.cv.api;

import be.mathiasbosman.cv.dto.UserDto;
import be.mathiasbosman.cv.oauth2.OAuth2Attribute;
import be.mathiasbosman.cv.oauth2.OAuth2Provider;
import be.mathiasbosman.cv.oauth2.OAuth2Service;
import be.mathiasbosman.cv.service.UserService;
import be.mathiasbosman.cv.util.WebUtils;
import java.util.Set;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class AuthController {

  private final OAuth2Service oAuth2Service;
  private final UserService userService;

  public AuthController(OAuth2Service oAuth2Service, UserService userService) {
    this.oAuth2Service = oAuth2Service;
    this.userService = userService;
  }

  /**
   * Returns a list of OAuth2Providers that are configured in the backend
   *
   * @return Set of OAuth2Providers configuredin the backend
   */
  @GetMapping("/providers")
  public Set<OAuth2Provider> providers() {
    return oAuth2Service.getRegisteredProviders();
  }

  /**
   * Returns the data of the logged in user
   *
   * @param user The OAuth2User, injected
   * @return UserDto with user info
   */
  @GetMapping("/user")
  public UserDto getUser(@AuthenticationPrincipal OAuth2User user) {
    if (user == null) {
      return null;
    }

    String email = oAuth2Service.getStringAttribute(WebUtils.token(), OAuth2Attribute.EMAIL);
    return userService.getUserByEmail(email);
  }
}
