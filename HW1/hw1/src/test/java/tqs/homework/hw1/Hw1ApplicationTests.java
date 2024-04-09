package tqs.homework.hw1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest
class Hw1ApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> {
            // if context was successfully loaded, this block will be executed
        });
	}

}
