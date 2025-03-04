package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWrongProduct() {
        Product product1 = new Product();
        product1.setProductName("DUMMY1");
        product1.setProductQuantity(1);
        product1.setProductID("DUMMY1_ID");

        Iterator<Product> iterator = Arrays.asList(product1).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        assertNull(productService.getItemByID("DUUMYX_ID"));
    }

    @Test
    void getProduct() {
        Product product1 = new Product();
        product1.setProductName("DUMMY1");
        product1.setProductQuantity(1);
        product1.setProductID("DUMMY1_ID");

        Iterator<Product> iterator = Arrays.asList(product1).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        assertEquals("DUMMY1", productService.getItemByID(product1.getProductID()).getProductName());
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductName("DUMMY");
        product.setProductQuantity(1);

        when(productRepository.create(product)).thenReturn(product);
        Product productBaru = productService.create(product);

        assertNotNull(productBaru);
        assertNotNull(productBaru.getProductID());
        assertEquals("DUMMY", productBaru.getProductName());
        assertEquals(1, productBaru.getProductQuantity());

//        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        Product product1 = new Product();
        product1.setProductName("DUMMY1");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductName("DUMMY2");
        product2.setProductQuantity(2);

        Iterator<Product> iterator = Arrays.asList(product1, product2).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> productList = productService.findAll();

        assertEquals(2, productList.size());
        assertEquals("DUMMY1", productList.get(0).getProductName());
        assertEquals("DUMMY2", productList.get(1).getProductName());
    }

    @Test
    void testDeleteProduct() {
        Product dummyUnknownProduct = new Product();
        dummyUnknownProduct.setProductName("DUMMY");
        dummyUnknownProduct.setProductQuantity(1);
        dummyUnknownProduct.setProductID("DUMMY_ID");

//        doNothing().when(productRepository).delete(dummyUnknownProduct);
        Iterator<Product> iterator = Arrays.asList(dummyUnknownProduct).iterator();
        when(productRepository.findAll()).thenReturn(iterator);
        productService.removeByID(dummyUnknownProduct.getProductID());

        verify(productRepository, times(1));
    }

    @Test
    void testDeleteWrongProduct() {
        Product dummyUnknownProduct = new Product();
        dummyUnknownProduct.setProductName("DUMMY");
        dummyUnknownProduct.setProductQuantity(1);
        dummyUnknownProduct.setProductID("DUMMY_ID");

        Iterator<Product> iterator = Arrays.asList(dummyUnknownProduct).iterator();
        when(productRepository.findAll()).thenReturn(iterator);
        productService.removeByID("DUMMYX_ID");

    }

    @Test
    void testEditProduct() {
        Product productBefore = new Product();
        productBefore.setProductName("DUMMY_BEFORE");
        productBefore.setProductQuantity(1);
        productBefore.setProductID("DUMMY_BEFORE_ID");

        Iterator<Product> iterator = Arrays.asList(productBefore).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        Product productAfter = new Product();
        productAfter.setProductName("DUMMY_AFTER");
        productAfter.setProductQuantity(1);
        productAfter.setProductID("DUMMY_BEFORE_ID");

        productService.editItem(productAfter);
    }

    @Test
    void testEditWrongProduct() {
        Product productBefore = new Product();
        productBefore.setProductName("DUMMY_BEFORE");
        productBefore.setProductQuantity(1);
        productBefore.setProductID("DUMMY_BEFORE_ID");

        Iterator<Product> iterator = Arrays.asList(productBefore).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        Product productAfter = new Product();
        productAfter.setProductName("DUMMY_AFTER");
        productAfter.setProductQuantity(1);
        productAfter.setProductID("DUMMY_AFTER_ID");

        productService.editItem(productAfter);
    }
}