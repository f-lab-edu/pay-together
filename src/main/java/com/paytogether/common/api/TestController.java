package com.paytogether.common.api;

import com.paytogether.exception.PayTogetherErrors;
import com.paytogether.exception.PayTogetherException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/test")
  public String testMethod() {
    return "Hello World";
  }

  @GetMapping("/run-error")
  public void runTimeErrorMethod() {
    throw new RuntimeException();
  }

  @GetMapping("/custom-error")
  public void customErrorMethod() {
    throw new PayTogetherException(PayTogetherErrors.INVALID_INFO);
  }
}
