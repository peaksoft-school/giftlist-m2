package kg.giftlist.giftlistm2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("restricted")
    public String restricted() {
        return "to see this text you need to be logged in!";
    }
}
