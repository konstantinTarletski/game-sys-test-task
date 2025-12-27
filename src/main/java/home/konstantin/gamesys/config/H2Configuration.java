package home.konstantin.gamesys.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Slf4j
public class H2Configuration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        log.info("H2 server created");
        System.setProperty("h2.bindAddress", "0.0.0.0"); //fix for "amazoncorretto:11-al2023-headless"
        return Server.createTcpServer(
                "-tcp", "-tcpAllowOthers",
                "-tcpPort", "9090",
                "-tcpAddress", "0.0.0.0"
        );

    }

}
