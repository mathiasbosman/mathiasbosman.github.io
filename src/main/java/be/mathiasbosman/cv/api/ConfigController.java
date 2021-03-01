package be.mathiasbosman.cv.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

  /**
   * Controller that is used by the Google Cloud Platform to "warmup" new instances. Basically
   * /_ah/warmup receives a GET request which will startup Spring Boot if not yet the case.
   * Otherwise it will just return a HTTP 200
   *
   * @return boolean true
   */
  @GetMapping("/_ah/warmup")
  public boolean warmup() {
    return true;
  }

}
