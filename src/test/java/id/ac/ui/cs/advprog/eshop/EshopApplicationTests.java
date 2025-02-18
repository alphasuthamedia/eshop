package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    /*
    Test if everything is callable
    it means, we have to test the grandfather of the grandfather of this project is working
     */
    @Test
    public void applicationContextTest() {
        EshopApplication.main(new String[] {});
    }
}
