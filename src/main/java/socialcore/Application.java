package socialcore;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Value("${api.key}")
    private String apiKey;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public GeoApiContext geoApiContext() {

        GeoApiContext.Builder builder = new GeoApiContext.Builder();
        return builder
                .apiKey(apiKey)
                .maxRetries(1)
                .build();

    }

}