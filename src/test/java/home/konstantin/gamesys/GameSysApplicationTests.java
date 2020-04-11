package home.konstantin.gamesys;

import home.konstantin.gamesys.model.Rss;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class GameSysApplicationTests {

	@Test
	void contextLoads() {
	}

	public Rss getRrs(){
		return Rss.builder().description("3333").title("tttt").uri("54645645").publishedDate(
			LocalDateTime.now()).build();
	}

}
