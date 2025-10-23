package tech.miladsadeghi.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tech.miladsadeghi.accounts.dtos.AccountsContactInfoDTO;

@EnableConfigurationProperties(value = {AccountsContactInfoDTO.class})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts MicroService RestAPI Documentation",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Milad Sadeghi",
                        email = "miladsadeghidm@gmail.com",
                        url = "https://miladsadeghi.tech"
                ),
                description = "This is a sample Accounts MicroService RESTful API documentation generated using OpenAPI 3.",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
