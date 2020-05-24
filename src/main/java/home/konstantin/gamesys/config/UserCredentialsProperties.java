package home.konstantin.gamesys.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "user-credentials")
public class UserCredentialsProperties {

    private String username;
    private String passwordHash;
    private String role;

}
