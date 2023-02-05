package by.zelezinsky.piris.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("default")
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Piris labs")
                        .description("Open Api")
                        .version("3.0")
                        .license(new License()
                                .name("MIT Licence")
                                .url("https://opensource.org/licenses/mit-license.php")
                        )
                        .contact(new Contact()
                                .email("ivan.zelezinsky@gmail.com")
                                .name("Ivan Zelezinsky")
                        )
                );
    }
}
