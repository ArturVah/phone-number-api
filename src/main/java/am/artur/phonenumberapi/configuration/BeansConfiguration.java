package am.artur.phonenumberapi.configuration;

import am.artur.phonenumberapi.model.Country;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BeansConfiguration {

    @Scope(value = "prototype")
    @Bean
    public Map<String, Country> callingCodesMap() {
        return new ConcurrentHashMap<>();
    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector()).build();
    }
}
