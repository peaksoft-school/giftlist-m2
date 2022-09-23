package kg.giftlist.giftlistm2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class GiftlistM2Application {

    public static void main(String[] args) {
        SpringApplication.run(GiftlistM2Application.class, args);
        System.out.println("Welcome colleagues, project name is Giftlist-M2!");
    }

    @GetMapping("/")
    public String greetingPage() {
        return "<h1>Welcome to Giftlist-M2 Application!!!<h1/>";
    }

}
