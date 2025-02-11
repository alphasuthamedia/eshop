package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Product;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// cek null

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /*
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);

        driver.navigate().to(baseUrl+"/product/list");

        assertEquals("Product' List", driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    void navigateCreateProductPage_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);

        driver.navigate().to(baseUrl+"/product/list");

        // check if the button clickable
        assertEquals("Create New Product", canClickTheButton_test(driver));

    }

    @Test
    void createNewProduct_isCorrect(ChromeDriver driver) throws Exception {
        assertNotNull(createProduct_test(driver));
    }

    String canClickTheButton_test(ChromeDriver driver){
        // Exercise
        WebElement createProductButton = driver.findElement(By.id("create-product"));
        createProductButton.click();

        // Verify --> shall be directed to create new product page (check the title)
        String pageTitle = driver.findElement(By.tagName("h3")).getText();
        return pageTitle;
    }

    String createProduct_test(ChromeDriver driver){
        driver.get(baseUrl);
        driver.navigate().to(baseUrl+"/product/create");

        WebElement addProductName = driver.findElement(By.id("create-nameInput"));
        addProductName.sendKeys("DUMMY_NAME");

        WebElement addProductQuantity = driver.findElement(By.id("create-quantityInput"));
        addProductQuantity.sendKeys("64");

        WebElement saveButton = driver.findElement(By.id("submit-button"));
        saveButton.click();

        String newProductName = driver.findElement(By.className("DUMMY_NAME"+","+"64")).getText();

        return newProductName;
    }

}
