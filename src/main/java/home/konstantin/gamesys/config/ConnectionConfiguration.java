package home.konstantin.gamesys.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class ConnectionConfiguration {

    private String driverClassName;
    private String username;
    private String password;
    private String url;

}
