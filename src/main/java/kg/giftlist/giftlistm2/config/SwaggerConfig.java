package kg.giftlist.giftlistm2.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "Authorization",
        description = "Paste here your JWT-token",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(apiInfo());
    }

    public Info apiInfo() {
        Info info = new Info();
        info
                .title("Giftlist API")
                .description("This project is developed by Peaksoft Moscow students <br />" +
                        "Gift application - You choose what to give as a gift!")
                .version("v1.0.0")
                .contact(new Contact().name("Peaksoft")
                        .url("https://peaksoft.us")
                        .email("hello@peaksoft.us"));
        return info;
    }

}