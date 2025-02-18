package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateProductTest {

    @InjectMocks
    ProductRepository productRepository;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        assertNotNull(productRepository.create(product));
    }

    @Test
    void testCreateInvalidProduct() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(-100);

        assertNull(productRepository.create(product));
    }
}
