package xmu.oomall.topic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        return httpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

}
