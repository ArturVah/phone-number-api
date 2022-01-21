package am.artur.phonenumberapi;

import am.artur.phonenumberapi.service.WikipediaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = WikipediaProperties.class)
@SpringBootApplication
public class PhoneNumberApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneNumberApiApplication.class, args);
    }
}
