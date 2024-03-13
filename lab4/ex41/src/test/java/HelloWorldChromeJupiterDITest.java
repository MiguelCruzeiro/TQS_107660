import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class HelloWorldChromeJupiterDITest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(FirefoxDriver driver) {
        // Exercise
        String sutUrl = "https://www.adidas.pt/";
        driver.get(sutUrl); 
        String title = driver.getTitle(); 
        log.debug("The title of {} is {}", sutUrl, title); 

        // Verify
        assertThat(title).isEqualTo("adidas Loja oficial Portugal | Roupa desportiva"); 
    }

}
