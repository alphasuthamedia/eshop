package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductTest {

    @InjectMocks
//    ProductRepository productRepository;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
    }

    @Test
    void testDeleteProduct() {
        Iterator iterator = productRepository.findAll();

        Product selectedProduct = null;
        while (iterator.hasNext()) {
            if (iterator.next().equals("eb558e9f-1c39-460e-8860-71af6af63bd6")) {
                selectedProduct = (Product) iterator.next();
                break;
            }
        }

        productRepository.delete(selectedProduct);
        selectedProduct = null;

        while (iterator.hasNext()) {
            if (iterator.next().equals("eb558e9f-1c39-460e-8860-71af6af63bd6")) {
                selectedProduct = (Product) iterator.next();
                break;
            }
        }

        assertNull(selectedProduct);
    }
}
